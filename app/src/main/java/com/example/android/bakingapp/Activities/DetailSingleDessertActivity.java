package com.example.android.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.FragmentDetailSingleDessert;
import com.example.android.bakingapp.Fragments.FragmentDetailStep;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class DetailSingleDessertActivity extends AppCompatActivity {

    public final String LOG_TAG = DetailSingleDessertActivity.class.getSimpleName();
    Recipe recipe;
    Step step;

    List<Step> stepList = new ArrayList<>();
    List<Ingredient> ingredientsList = new ArrayList<>();
    String recipeShortDescription;
    String recipeDescription;
    int stepId;
    int image;
    public int position = 0;
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_single_dessert);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                recipe = bundle.getParcelable("list");
                image = bundle.getInt("images");
                position = bundle.getInt("position");
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            if (findViewById(R.id.tablet_view_linear_layout) != null) {
                isTablet = true;
            } else {
                isTablet = false;
            }

            FragmentDetailSingleDessert fragmentDetailSingleDessert = new FragmentDetailSingleDessert();
            FragmentDetailStep fragmentDetailStep = new FragmentDetailStep();

            if (isTablet) {
                fragmentDetailSingleDessert.setRecipe(position, recipe, image, isTablet);
                fragmentManager.beginTransaction()
                        .add(R.id.container_detail_recipe, fragmentDetailSingleDessert)
                        .commit();
                fragmentDetailStep.setRecipeStepDetails(recipeShortDescription,
                        recipeDescription, stepId, null, null);
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container_step_recipe, fragmentDetailStep)
                        .commit();
            } else if (!isTablet) {
                fragmentDetailSingleDessert.setRecipe(position, recipe, image, isTablet);
                fragmentManager.beginTransaction()
                        .add(R.id.container_detail_recipe, fragmentDetailSingleDessert)
                        .commit();
            }
        }
    }
}