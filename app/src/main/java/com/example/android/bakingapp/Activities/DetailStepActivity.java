package com.example.android.bakingapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.FragmentDetailStep;
import com.example.android.bakingapp.R;

public class DetailStepActivity extends AppCompatActivity {

    public final String LOG_TAG = DetailStepActivity.class.getSimpleName();
    String recipeShortDescription;
    String recipeDescription;
    int stepId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step_instructions);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentDetailStep fragmentDetailStep = new FragmentDetailStep();
        fragmentDetailStep.setRecipeStepDetails(recipeShortDescription, recipeDescription,
                stepId, null, null);

        fragmentManager.beginTransaction()
                .add(R.id.detail_container_step_recipe, fragmentDetailStep)
                .commit();
    }
}
