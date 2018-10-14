package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

import static com.example.android.bakingapp.Utils.RecipesImageAssets.recipeImages;

public class FragmentMasterListRecipe extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Recipe>>,
        RecipeClickListener {

    private final String LOG_TAG = FragmentMasterListRecipe.class.getSimpleName();
    public static final String URL_RECIPE_REQUEST = "https://d17h27t6h515a5.cloudfront" +
            ".net/topher/2017/May/59121517_baking/baking.json";
    private List<Recipe> recipeListItem;
    private List<Recipe> list;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    // mandatory empty constructor
    public FragmentMasterListRecipe() {
    }

    // Inflates the fragment layout and sets resources
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the MainRecipeFragment layout
        View rootView = inflater.inflate(R.layout.fragment_master_list_recipe, container, false);

        this.recyclerView = rootView.findViewById(R.id.recycler_view);

        if (rootView.findViewById(R.id.tablet_view) != null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        recipeAdapter = new RecipeAdapter(getActivity(), this);
        recyclerView.setAdapter(recipeAdapter);
        getLoaderManager().initLoader(1, null, this);

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
        list = data;
        recipeAdapter.InsertListItems(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {
        recipeListItem.clear();
    }

    @Override
    public void onRecipeClick(int position) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), DetailSingleDessertActivity.class);
        bundle.putParcelable("list", list.get(position));
        bundle.putInt("images", recipeImages[position]);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
