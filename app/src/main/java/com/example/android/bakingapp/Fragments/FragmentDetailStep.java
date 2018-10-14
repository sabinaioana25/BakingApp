package com.example.android.bakingapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapp.Activities.DetailStepActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

public class FragmentDetailStep extends Fragment {

    public final String LOG_TAG = DetailStepActivity.class.getSimpleName();
    Step step;
    Recipe recipe;
    Button previousButton;
    Button nextButton;
    String recipeShortDescription;
    String recipeDescription;
    String videoUrl;
    String thumbnailUrl;
    int stepId;
    boolean isTablet;
    int position;

    // mandatory empty constructor
    public FragmentDetailStep() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);

        previousButton = rootView.findViewById(R.id.detail_activity_step_button_previous);
        nextButton = rootView.findViewById(R.id.detail_activity_step_button_next);

        if (rootView.findViewById(R.id.tablet_view_linear_layout) != null) {
            isTablet = true;
            previousButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        } else if (rootView.findViewById(R.id.phone_view_linear_layout) != null) {
            isTablet = false;
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (intent != null) {
            if (bundle != null) {
                step = bundle.getParcelable("instructions");
                if (step != null) {
                    stepId = step.getStepId();
                    recipeShortDescription = step.getShortDescription();
                    recipeDescription = step.getDescription();
                    videoUrl = step.getVideoUrl();
                    thumbnailUrl = step.getThumbnailUrl();
                }
            }
        }

        // find and display values
        final TextView recipeShortDescriptionTextView = rootView.findViewById(R.id.detail_activity_step_short_description);
        final TextView recipeDescriptionTextView = rootView.findViewById(R.id.detail_activity_step_description);
        recipeShortDescriptionTextView.setText(String.valueOf(stepId) + " " + recipeShortDescription);
        recipeDescriptionTextView.setText(recipeDescription);

        // return view
        return rootView;
    }

    public void setRecipeStepDetails(String shortDescription, String recipeDescription, int
            stepId, String videoUrl, String thumbnailUrl) {
        this.recipeShortDescription = shortDescription;
        this.recipeDescription = recipeDescription;
        this.stepId = stepId;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
}

