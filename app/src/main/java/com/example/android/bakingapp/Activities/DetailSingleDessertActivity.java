package com.example.android.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.bakingapp.Fragments.FragmentDetailSingleDessert;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Widget.BakingService;
import com.example.android.bakingapp.model.Recipe;

@SuppressWarnings("unused")
public class DetailSingleDessertActivity extends AppCompatActivity {

    // constants
    private static final String LIST = "list";
    private static final String IMAGES = "images";
    private static final String POSITION = "position";
    private static final String TWO_PANE = "twoPaneMode";

    // --Commented out by Inspection (11/14/2018 7:53 PM):public final String LOG_TAG =
    // DetailSingleDessertActivity.class.getSimpleName();
    Recipe recipe;
    public static int recipeId;
    public static String recipeName;
    int image;
    int position;
    boolean twoPane;
    int stepId;
    String stepShortDescription;
    String stepDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_single_dessert);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                recipe = bundle.getParcelable(LIST);
                image = bundle.getInt(IMAGES);
                twoPane = bundle.getBoolean(TWO_PANE);
                position = bundle.getInt(POSITION);
            }

            recipeName = recipe.getName();
            recipeId = recipe.getRecipeId();
            stepId = recipe.getSteps().get(position).getStepId();
            stepShortDescription = recipe.getSteps().get(position).getShortDescription();
            stepDescription = recipe.getSteps().get(position).getDescription();

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (savedInstanceState == null) {

                // create corresponding fragment
                FragmentDetailSingleDessert fragmentDetailSingleDessert = new
                        FragmentDetailSingleDessert();

                // setter methods
                fragmentDetailSingleDessert.setDetailRecipe(recipe, image, twoPane);
                fragmentDetailSingleDessert.setDetailRecipe1(stepId, stepShortDescription,
                        stepDescription);

                fragmentManager.beginTransaction()
                        .add(R.id.container_detail_recipe, fragmentDetailSingleDessert)
                        .commit();
            }
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(recipeName);
    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_detail_activity, menu);
        return true;
    }

    @SuppressWarnings("UnusedAssignment")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        //noinspection UnusedAssignment
        boolean addedRecipe = true;

        if (itemId == R.id.action_add_to_widget) {
            FragmentDetailSingleDessert.ingredients = recipe.getIngredients();
            addedRecipe = BakingService.startAddRecipes(this);

            // check if the recipe was added to the widget
            if (addedRecipe) {
                Toast.makeText(this, R.string.confirmation_message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.not_added_to_widget_message, Toast.LENGTH_SHORT)
                        .show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
