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
import com.example.android.bakingapp.model.Recipe;

public class FragmentDetailSingleDessert extends Fragment implements StepAdapter.StepClickListener {

    private static final String LOG_TAG = FragmentDetailSingleDessert.class.getSimpleName();
    public static final String IMAGES_KEY = "images";
    IngredientAdapter ingredientAdapter;
    StepAdapter stepAdapter;
    RecyclerView ingredientRecyclerView;
    RecyclerView stepRecyclerView;
    public Recipe recipe;
    ImageView imageView;
    int position;
    int image;
    boolean isTablet;


    // mandatory empty constructor
    public FragmentDetailSingleDessert() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_detail_dessert, container, false);

        // main image of recipe
        imageView = rootView.findViewById(R.id.detail_image_dessert);
        imageView.setImageResource(image);

        // ingredients
        this.ingredientRecyclerView = rootView.findViewById(R.id
                .ingredient_list_recyler_view);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ingredientAdapter = new IngredientAdapter(getContext(), recipe.getIngredients());
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        // steps
        this.stepRecyclerView = rootView.findViewById(R.id.
                step_list_recycler_view);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepRecyclerView.setItemAnimator(new DefaultItemAnimator());
        stepAdapter = new StepAdapter(getContext(), recipe.getSteps(), this);
        stepRecyclerView.setAdapter(stepAdapter);

        if (rootView.findViewById(R.id.tablet_view_linear_layout) != null) {
            isTablet = true;
        } else if (rootView.findViewById(R.id.phone_view_linear_layout) != null) {
            isTablet = false;
        }
        // return view
        return rootView;
    }

    @Override
    public void onStepListItemClick(int clickItemIndex) {

        if (isTablet) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentDetailStep fragmentDetailStep = new FragmentDetailStep();

            fragmentDetailStep.setRecipeStepDetails(
                    recipe.getSteps().get(clickItemIndex).getShortDescription(),
                    recipe.getSteps().get(clickItemIndex).getDescription(),
                    recipe.getSteps().get(clickItemIndex).getStepId(),
                    null,
                    null);

            fragmentManager.beginTransaction()
                    .replace(R.id.detail_container_step_recipe, fragmentDetailStep)
                    .commit();

        } else if (!isTablet) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("instructions", recipe.getSteps().get(clickItemIndex));
            Intent intent = new Intent(getActivity(), DetailStepActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void setRecipe(int position, Recipe recipe, int image, boolean isTablet) {
        this.position = position;
        this.recipe = recipe;
        this.image = image;
        this.isTablet = isTablet;
    }
}

