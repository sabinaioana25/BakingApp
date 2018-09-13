package com.example.android.bakingapp.Activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.Adapter.RecipeAdapter;
import com.example.android.bakingapp.Loader.RecipeLoader;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.RecipesImageAssets;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Recipe>>,
        RecipeAdapter.RecipeClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    public static final String URL_RECIPE_REQUEST = "https://d17h27t6h515a5.cloudfront" +
            ".net/topher/2017/May/59121517_baking/baking.json";
    public static final String IMAGES_KEY = "images";

    private List<Recipe> recipeListItem;
    private List<Recipe> list;

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recipeAdapter = new RecipeAdapter(this, this);
        recyclerView.setAdapter(recipeAdapter);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle bundle) {
        return new RecipeLoader(getApplicationContext(), URL_RECIPE_REQUEST);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        list = recipes;
        recipeAdapter.InsertListItems(recipes);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        recipeListItem.clear();
    }

    @Override
    public void onRecipeClick(int position) {

        Bundle bundle = new Bundle();
        Intent intent = new Intent(MainActivity.this, DetailSingleDessertActivity.class);
        bundle.putParcelable("list", list.get(position));
        bundle.putInt("images", RecipesImageAssets.recipeImages[position]);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
