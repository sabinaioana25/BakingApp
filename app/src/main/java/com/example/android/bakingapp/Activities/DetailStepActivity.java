package com.example.android.bakingapp.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bakingapp.Fragments.FragmentDetailStep;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("EmptyMethod")
public class DetailStepActivity extends AppCompatActivity {

    private static final String RECIPE = "recipe";
    private static final String CLICK_INDEX = "clickItemIndex";
    // --Commented out by Inspection (11/14/2018 8:03 PM):public final String LOG_TAG = DetailStepActivity.class.getSimpleName();
    Recipe recipe;
    int position;
    public int stepIndex;
    String stepShortDescription;
    String stepDescription;
    String stepVideoUrl;
    String stepThumbnailUrl;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @BindView(R.id.detail_activity_step_button_previous)
    Button prevStepButton;
    @BindView(R.id.detail_activity_step_button_next)
    Button nextStepButton;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step_instructions);

        ButterKnife.bind(this);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            recipe = bundle.getParcelable(RECIPE);
            position = bundle.getInt(CLICK_INDEX);
        }

        stepIndex = recipe.getSteps().get(position).getStepId();
        stepShortDescription = recipe.getSteps().get(position).getShortDescription();
        stepDescription = recipe.getSteps().get(position).getDescription();
        stepVideoUrl = recipe.getSteps().get(position).getVideoUrl();
        stepThumbnailUrl = recipe.getSteps().get(position).getThumbnailUrl();

        if (savedInstanceState == null) {
            FragmentDetailStep fragmentDetailStep = new FragmentDetailStep();
            fragmentDetailStep.setStepIndex(
                    stepIndex,
                    stepShortDescription,
                    stepDescription,
                    stepVideoUrl,
                    stepThumbnailUrl);
            fragmentManager.beginTransaction()
                    .add(R.id.container_detail_step, fragmentDetailStep)
                    .commit();
            fragmentDetailStep.setRecipeStep(recipe, position);
        }

        prevStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((stepIndex <= recipe.getSteps().size() - 1) && stepIndex > 0) {
                    stepIndex--;
                } else {
                    Toast.makeText(getApplicationContext(), R.string.first_step_toast_message, Toast
                            .LENGTH_SHORT).show();
                }
                FragmentDetailStep fragmentDetailStep = new FragmentDetailStep();
                fragmentDetailStep.setStepIndex(
                        stepIndex,
                        recipe.getSteps().get(stepIndex).getShortDescription(),
                        recipe.getSteps().get(stepIndex).getDescription(),
                        recipe.getSteps().get(stepIndex).getVideoUrl(),
                        recipe.getSteps().get(stepIndex).getThumbnailUrl());

                fragmentManager.beginTransaction()
                        .replace(R.id.container_detail_step, fragmentDetailStep)
                        .commit();
            }
        });

        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepIndex < recipe.getSteps().size() - 1) {
                    stepIndex++;
                } else {
                    Toast.makeText(getApplicationContext(), R.string.last_message_toast_message, Toast
                            .LENGTH_SHORT).show();
                }

                FragmentDetailStep fragmentDetailStep1 = new FragmentDetailStep();
                fragmentDetailStep1.setStepIndex(
                        stepIndex,
                        recipe.getSteps().get(stepIndex).getShortDescription(),
                        recipe.getSteps().get(stepIndex).getDescription(),
                        recipe.getSteps().get(stepIndex).getVideoUrl(),
                        recipe.getSteps().get(stepIndex).getThumbnailUrl());

                fragmentManager.beginTransaction()
                        .replace(R.id.container_detail_step, fragmentDetailStep1)
                        .commit();
            }
        });
    }

    // media receiver class
    public static class MediaReceiver extends BroadcastReceiver {
        public MediaReceiver() {}

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(FragmentDetailStep.mediaSession, intent);
        }
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
