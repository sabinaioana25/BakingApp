package com.example.android.bakingapp.Fragments;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentDetailStep extends Fragment implements ExoPlayer.EventListener {

    private static final String LOG_TAG = FragmentDetailStep.class.getSimpleName();
    private static final String VIDEO_FILE_EXT = ".mp4";
    private Recipe recipe;
    private int position;
    private int stepIndex;
    private String stepShortDescription;
    private String stepDescription;
    private String stepVideoUrl;
    private String stepThumbnailUrl;
    @BindView(R.id.detail_activity_step_short_description)
    TextView recipeShortDescriptionTextView;
    @BindView(R.id.detail_activity_step_description)
    TextView recipeDescriptionTextView;
    @BindView(R.id.detail_activity_step_thumbnail)
    ImageView thumbnailImageView;
    private SimpleExoPlayer exoPlayer;
    @BindView(R.id.detail_activity_step_video)
    SimpleExoPlayerView exoPlayerView;
    public static MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    @BindView(R.id.exo_progress_bar)
    ProgressBar exoPlayerProgressBar;
    private NotificationManager notificationManager;

    //savedInstanceState
    private static final String SAVED_STATE_EXO_POSITION = "savedStateExoPosition";
    private static final String SHORT_DESCRIPTION_KEY = "savedShortDescription";
    private static final String DESCRIPTION_KEY = "savedDescription";

    private static Bundle mSavedState = null;
    long videoPosition = 0;

    // mandatory empty constructor
    public FragmentDetailStep() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);
        mSavedState = savedInstanceState;
        setRetainInstance(true);

        if (mSavedState != null) {
            videoPosition = savedInstanceState.getLong(SAVED_STATE_EXO_POSITION);
            stepShortDescription = savedInstanceState.getString(SHORT_DESCRIPTION_KEY);
            stepDescription = savedInstanceState.getString(DESCRIPTION_KEY);
        }
        // initialize the Media Session
        initializeMediaSession();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        // bind views
        ButterKnife.bind(this, rootView);

        if (stepShortDescription.equals("")) {
            recipeDescriptionTextView.setText("");
        } else {
            recipeShortDescriptionTextView.setText(stepIndex + ". " + stepShortDescription);
        }

        if (stepIndex == 0) {
            recipeDescriptionTextView.setText("");
        } else {
            recipeDescriptionTextView.setText(stepDescription.substring(3));
        }

        if (stepVideoUrl.equals("") && (stepThumbnailUrl.equals(""))) {
            exoPlayerProgressBar.setVisibility(View.GONE);
            exoPlayerView.setVisibility(View.GONE);
            thumbnailImageView.setVisibility(View.GONE);

        } else if (stepVideoUrl.equals("") && stepThumbnailUrl.endsWith(VIDEO_FILE_EXT)) {
            exoPlayerProgressBar.setVisibility(View.VISIBLE);
            exoPlayerView.setVisibility(View.VISIBLE);
        } else if (!stepVideoUrl.equals("") && stepThumbnailUrl.equals("")) {
            thumbnailImageView.setVisibility(View.GONE);
            exoPlayerProgressBar.setVisibility(View.VISIBLE);
            exoPlayerView.setVisibility(View.VISIBLE);
        }

        // initialize the media player
        initializeMediaPlayer(Uri.parse(stepVideoUrl));
    }

    public void setRecipeStep(Recipe recipe, int position) {
        this.recipe = recipe;
        this.position = position;
    }

    public void setStepIndex(int stepIndex, String stepShortDescription, String stepDescription,
                             String stepVideoUrl, String stepThumbnailUrl) {
        this.stepIndex = stepIndex;
        this.stepShortDescription = stepShortDescription;
        this.stepDescription = stepDescription;
        this.stepVideoUrl = stepVideoUrl;
        this.stepThumbnailUrl = stepThumbnailUrl;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (videoPosition != 0) {
            exoPlayer.seekTo(videoPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            videoPosition = exoPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.getLong(SAVED_STATE_EXO_POSITION);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        videoPosition = 0;
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            releasePlayer();
            mediaSession.setActive(false);
        }
    }

    // release the ExoPlayer
    private void releasePlayer() {
        notificationManager.cancelAll();
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SAVED_STATE_EXO_POSITION, videoPosition);
        outState.putString(SHORT_DESCRIPTION_KEY, stepShortDescription);
        outState.putString(DESCRIPTION_KEY, stepDescription);
    }

    private void initializeMediaPlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // create an instance of the exoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector,
                    loadControl);
            exoPlayerView.setPlayer(exoPlayer);

            if (mSavedState != null) {
                videoPosition = mSavedState.getLong(SAVED_STATE_EXO_POSITION);
                exoPlayer.seekTo(videoPosition);
            }

            // set the ExoPlayer.EventListener to the fragment
            exoPlayer.addListener(this);

            // prepare the MediaSource
            String userAgent = Util.getUserAgent(getContext(), "RecipeVideo");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new
                    DefaultDataSourceFactory(getContext(), userAgent), new
                    DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializeMediaSession() {

        // create a MediaSessionCompat object
        mediaSession = new MediaSessionCompat(getContext(), LOG_TAG);

        // enable callbacks from MediaButtons and TransportControls
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // MediaButtons don't restart the player when the app is not visible
        mediaSession.setMediaButtonReceiver(null);

        // set initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());

        // handle callbacks from a media controller
        mediaSession.setCallback(new MySessionCallback());

        // start the Media Session since the activity is on
        mediaSession.setActive(true);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_BUFFERING) {
            exoPlayerProgressBar.setVisibility(View.VISIBLE);
        } else {
            exoPlayerProgressBar.setVisibility(View.GONE);
        }
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());
        showNotification(stateBuilder.build());
    }

    // show MediaStyle notification with an action that depends on the current MediaSession
    // PlaybackState
    private void showNotification(PlaybackStateCompat state) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
        int icon;
        String play_pause;
        if (state.getState() == PlaybackStateCompat.STATE_PLAYING) {
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.pause);
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = getString(R.string.play);
        }

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause, MediaButtonReceiver.buildMediaButtonPendingIntent(getContext()
                , PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new NotificationCompat
                .Action(R.drawable.exo_controls_previous, getString(R.string.restart),
                MediaButtonReceiver.buildMediaButtonPendingIntent(getContext(),
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent
                (getActivity(), FragmentDetailStep.class), 0);
        builder
                .setContentTitle(getString(R.string.hear_instructions))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_slice_cake)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken())
                        .setShowActionsInCompactView(0, 1));

        notificationManager = (NotificationManager) getContext().getSystemService(Context
                .NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    @Override
    public void onSeekProcessed() {
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(videoPosition);
        }
    }
}