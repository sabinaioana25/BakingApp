package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Activities.DetailSingleDessertActivity;
import com.example.android.bakingapp.Adapter.RecipeAdapter;
import com.example.android.bakingapp.ClickListener.RecipeClickListener;
import com.example.android.bakingapp.Loader.RecipeLoader;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.NetworkDetection;
import com.example.android.bakingapp.Utils.RecipesImageAssets;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMasterListRecipe extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Recipe>>,
        RecipeClickListener {

    private static final String SAVED_INSTANCE_STATE_LIST = "recipe list";
    private static final String LIST_KEY = "list";
    private static final String IMAGE_KEY = "images";
    private static final String TWO_PANE_KEY = "twoPaneMode";
    private static final String POSITION_KEY = "position";

    private final String LOG_TAG = FragmentMasterListRecipe.class.getSimpleName();
    public static final String URL_RECIPE_REQUEST = "https://d17h27t6h515a5.cloudfront" + "" +
            ".net/topher/2017/May/59121517_baking/baking.json";
    boolean twoPane;
    public static List<Recipe> list = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    NetworkDetection networkDetection;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;

    // mandatory empty public constructor
    public FragmentMasterListRecipe() {
    }

    // Inflates the fragment layout and sets resources
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_STATE_LIST);
        }

        // check internet connectivity
        networkDetection = new NetworkDetection(getActivity());

        // Inflate the MainRecipeFragment layout
        View rootView = inflater.inflate(R.layout.fragment_master_list_recipe, container, false);

        ButterKnife.bind(this, rootView);
        if (networkDetection.isConnected()) {
            if (twoPane) {
                gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                recipeAdapter = new RecipeAdapter(getActivity(), this);
                recyclerView.setAdapter(recipeAdapter);

            } else if (!twoPane) {
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                recipeAdapter = new RecipeAdapter(getActivity(), this);
                recyclerView.setAdapter(recipeAdapter);
            }
        }
        getLoaderManager().initLoader(1, null, this).forceLoad();
        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable
            Bundle args) {
        return new RecipeLoader(getActivity(), URL_RECIPE_REQUEST);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        list.clear();
        list = data;
        if (networkDetection.isConnected()) {
            recipeAdapter.insertListItems(list);
            recipeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {
    }

    @Override
    public void onRecipeClick(int position) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), DetailSingleDessertActivity.class);
        bundle.putParcelable(LIST_KEY, list.get(position));
        bundle.putInt(IMAGE_KEY, RecipesImageAssets.recipeImageList.get(position));
        bundle.putBoolean(TWO_PANE_KEY, twoPane);
        bundle.putInt(POSITION_KEY, position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setTwoPane(boolean twoPane) {
        this.twoPane = twoPane;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(SAVED_INSTANCE_STATE_LIST, (ArrayList<? extends
                Parcelable>) list);
        super.onSaveInstanceState(outState);
    }
}