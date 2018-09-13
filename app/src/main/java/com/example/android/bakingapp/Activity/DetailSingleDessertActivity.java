package com.example.android.bakingapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.bakingapp.Adapter.IngredientAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class DetailSingleDessertActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailSingleDessertActivity.class.getSimpleName();
    Button buttonInstructions;
    List<Ingredient> ingredientsList;
    IngredientAdapter ingredientAdapter;
    RecyclerView ingredientRecyclerView;
    Recipe recipe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_single_dessert);

        ingredientsList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        recipe = bundle.getParcelable("list");
        ingredientsList = recipe.getIngredients();


        int image = bundle.getInt(MainActivity.IMAGES_KEY);
        ImageView imageView = (ImageView) findViewById(R.id.detail_image_dessert);
        imageView.setImageResource(image);

        this.ingredientRecyclerView = (RecyclerView) findViewById(R.id
                .ingredient_list_recyler_view);
        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter = new IngredientAdapter(this, ingredientsList);
        ingredientRecyclerView.setAdapter(ingredientAdapter);

//        ingredientAdapter.notifyDataSetChanged();
        buttonInstructions = (Button) findViewById(R.id.detail_button_instructions);

    }
}
