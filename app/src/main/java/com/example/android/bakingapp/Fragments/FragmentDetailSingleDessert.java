package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.bakingapp.Activities.DetailStepActivity;
import com.example.android.bakingapp.Adapter.IngredientAdapter;
import com.example.android.bakingapp.Adapter.StepAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.SimpleDividerItemDecoration;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentDetailSingleDessert extends Fragment implements StepAdapter.StepClickListener {

    private static final String LOG_TAG = FragmentDetailSingleDessert.class.getSimpleName();
    private static final String IMAGE_KEY = "image";
    private static final String STEP_LIST_KEY = "stepList";
    private static final String RECIPE_KEY = "recipe";
    private static final String CLICK_ITEM_INDEX_KEY = "clickItemIndex";
    private String INGREDIENT_LIST_KEY = "ingredientList";

    IngredientAdapter ingredientAdapter;
    StepAdapter stepAdapter;
    @BindView(R.id.ingredient_list_recyler_view)
    RecyclerView ingredientRecyclerView;
    @BindView(R.id.step_list_recycler_view)
    RecyclerView stepRecyclerView;
    Recipe recipe;
    @BindView(R.id.detail_image_dessert)
    ImageView imageView;
    int position;
    int image;
    public static String recipeName = "Recipe name";
    boolean twoPane;
    int stepId;
    String stepShortDescription;
    String stepDescription;

    public static List<Ingredient> ingredients = new ArrayList<Ingredient>();
    List<Step> steps = new ArrayList<Step>();

    // mandatory empty constructor
    public FragmentDetailSingleDessert() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_detail_dessert, container, false);
        setRetainInstance(true);

        if (savedInstanceState != null) {
            image = savedInstanceState.getInt(IMAGE_KEY);
            ingredients = savedInstanceState.getParcelableArrayList(INGREDIENT_LIST_KEY);
            steps = savedInstanceState.getParcelableArrayList(STEP_LIST_KEY);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        if (recipe != null) {
            ingredients = recipe.getIngredients();
            steps = recipe.getSteps();

            // bind views
            ButterKnife.bind(this, rootView);

            imageView.setImageResource(image);

            // ingredients
            ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ingredientRecyclerView.setItemAnimator(new DefaultItemAnimator());
            ingredientAdapter = new IngredientAdapter(getActivity(), recipe.getIngredients());
            ingredientRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity
                    ()));
            ingredientRecyclerView.setAdapter(ingredientAdapter);

            // steps
            stepRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            stepRecyclerView.setItemAnimator(new DefaultItemAnimator());
            stepAdapter = new StepAdapter(getActivity(), recipe.getSteps(), this);
            stepRecyclerView.setAdapter(stepAdapter);


            // check if app is running on a tablet
            if (twoPane) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentDetailStep fragmentDetailStep = new FragmentDetailStep();

                // setter methods
                fragmentDetailStep.setRecipeStep(recipe, position);
                fragmentDetailStep.setStepIndex(
                        recipe.getSteps().get(position).getStepId(),
                        recipe.getSteps().get(position).getShortDescription(),
                        recipe
                                .getSteps().get(position).getDescription(),
                        recipe.getSteps().get(position).getVideoUrl(),
                        recipe.getSteps().get(position).getThumbnailUrl());

                fragmentManager.beginTransaction()
                        .replace(R.id.container_detail_step, fragmentDetailStep)
                        .commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        int mImage = image;
        List<Ingredient> mIngredients = ingredients;
        List<Step> mSteps = steps;
        outState.putInt(IMAGE_KEY, mImage);
        outState.putParcelableArrayList(INGREDIENT_LIST_KEY, (ArrayList<Ingredient>) mIngredients);
        outState.putParcelableArrayList(STEP_LIST_KEY, (ArrayList<Step>) mSteps);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepListItemClick(int clickItemIndex) {
        if (twoPane) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentDetailStep fragmentDetailStep = new FragmentDetailStep();
            fragmentDetailStep.setRecipeStep(recipe, position);
            fragmentDetailStep.setStepIndex(
                    recipe.getSteps().get(clickItemIndex).getStepId(),
                    recipe.getSteps().get(clickItemIndex).getShortDescription(),
                    recipe.getSteps().get(clickItemIndex).getDescription(),
                    recipe.getSteps().get(clickItemIndex).getVideoUrl(),
                    recipe.getSteps().get(clickItemIndex).getThumbnailUrl());

            fragmentManager.beginTransaction()
                    .replace(R.id.container_detail_step, fragmentDetailStep)
                    .commit();

        } else if (!twoPane) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_KEY, recipe);
            bundle.putInt(CLICK_ITEM_INDEX_KEY, clickItemIndex);
            Intent intent = new Intent(getActivity(), DetailStepActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void setDetailRecipe(Recipe recipe, int image, boolean twoPane) {
        this.recipe = recipe;
        this.image = image;
        this.twoPane = twoPane;
    }

    public void setDetailRecipe1(int stepId, String stepShortDescription, String
            stepDescription) {
        this.stepId = stepId;
        this.stepShortDescription = stepShortDescription;
        this.stepDescription = stepDescription;
    }
}

