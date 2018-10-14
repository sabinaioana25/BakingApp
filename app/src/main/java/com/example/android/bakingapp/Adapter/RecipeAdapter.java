package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.ClickListener.RecipeClickListener;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.RecipesImageAssets;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static RecipeClickListener listener;

//    // interface
//    public static interface RecipeClickListener {
//        void onRecipeClick(int position);
//    }

    private final static String TAG = RecipeAdapter.class.getSimpleName();
    public Context context;
    private static List<Recipe> recipeList;

    public RecipeAdapter(Context context, RecipeClickListener
            recipeClickListener) {
        this.context = context;
        this.listener = recipeClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .activity_card_item, parent, false);
        return new RecipeViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipeList.get(position);
        holder.imageViewRecipe.setImageResource(RecipesImageAssets.recipeImages[position]);
        holder.textViewRecipeName.setText(recipe.getName());
        holder.textViewTotalServings.setText(recipe.getServings());
    }

    @Override
    public int getItemCount() {

        if (recipeList != null) {
            // return the size of the RecyclerView item
            return recipeList.size();
        } else
            return 0;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {

        ImageView imageViewRecipe;
        TextView textViewRecipeName;
        TextView textViewTotalServings;

        // constructor
        RecipeViewHolder(final View itemView) {
            super(itemView);

            imageViewRecipe = (ImageView) itemView.findViewById(R.id.image_dessert);
            textViewRecipeName = (TextView) itemView.findViewById(R.id.text_view_dessert_name);
            textViewTotalServings = (TextView) itemView.findViewById(R.id.text_view_servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                    listener.onRecipeClick(position);
            }
        }
    }

    public void InsertListItems(List<Recipe> recipeData) {
        this.recipeList = recipeData;
        notifyDataSetChanged();
    }
}
