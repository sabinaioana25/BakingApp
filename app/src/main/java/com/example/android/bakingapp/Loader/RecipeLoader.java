package com.example.android.bakingapp.Loader;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.android.bakingapp.Utils.RecipeJsonUtils;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeLoader extends android.support.v4.content.AsyncTaskLoader<List<Recipe>> {

    // --Commented out by Inspection (11/14/2018 8:01 PM):private static final String LOG_TAG = RecipeLoader.class.getSimpleName();
    private final String url;

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
