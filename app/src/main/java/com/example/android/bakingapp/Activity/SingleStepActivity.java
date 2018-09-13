package com.example.android.bakingapp.Activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.Adapter.StepAdapter;
import com.example.android.bakingapp.Loader.RecipeLoader;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class SingleStepActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Recipe>> {

    public final String TAG = SingleStepActivity.class.getSimpleName();
    private List<Step> stepList;
    private RecyclerView stepRecyclerView;
    private StepAdapter stepAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_single_dessert);

        stepList = new ArrayList<>();
//        this.stepRecyclerView = (RecyclerView) findViewById(R.id.step_recycler_view);
        stepRecyclerView.setHasFixedSize(true);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        stepAdapter = new StepAdapter(this, stepList);
        stepRecyclerView.setAdapter(stepAdapter);
        getLoaderManager().initLoader(2, null, this);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(getApplicationContext(), MainActivity.URL_RECIPE_REQUEST);
    }


    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        stepList.clear();
        stepAdapter.notifyDataSetChanged();

    }


    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        stepList.clear();
    }
}
