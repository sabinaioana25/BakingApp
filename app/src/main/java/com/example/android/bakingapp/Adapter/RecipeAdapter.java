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

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static RecipeClickListener listener;

    private final static String TAG = RecipeAdapter.class.getSimpleName();
    public final Context context;
    private static List<Recipe> recipeList;

    public RecipeAdapter(Context context, RecipeClickListener
            recipeClickListener) {
        this.context = context;
        listener = recipeClickListener;
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
        holder.imageViewRecipe.setImageResource(RecipesImageAssets.recipeImageList.get(position));
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

        @BindView(R.id.image_dessert)
        ImageView imageViewRecipe;
        @BindView(R.id.text_view_dessert_name)
        TextView textViewRecipeName;
        @BindView(R.id.text_view_servings)
        TextView textViewTotalServings;

        // constructor
        RecipeViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
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

    public void insertListItems(List<Recipe> recipeData) {
        this.recipeList = recipeData;
        notifyDataSetChanged();
    }
}