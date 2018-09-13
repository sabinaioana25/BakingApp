package com.example.android.bakingapp.Loader;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.bakingapp.Utils.RecipeJsonUtils;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {

    private String url;
    private static final String TAG = RecipeLoader.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public RecipeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {
       return RecipeJsonUtils.getRecipeData(url);
    }
}
