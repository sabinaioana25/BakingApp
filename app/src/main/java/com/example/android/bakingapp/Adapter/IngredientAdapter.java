package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter
        .IngredientViewHolder> {

    final static String LOG_TAG = Ingredient.class.getSimpleName();
    public Context context;
    private static List<Ingredient> ingredientList;

    public IngredientAdapter(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View ingredientLayout = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_detail_ingredients_list_item, parent, false);
        return new IngredientViewHolder(ingredientLayout);
    }

    @Override
    public int getItemCount() {
        if (ingredientList != null) {
            return ingredientList.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredientList.get(position);
        holder.ingredientQuantityTextView.setText(ingredient.getQuantity());
        holder.ingredientMeasureTextView.setText(ingredient.getMeasure());
        holder.ingredientTypeTextView.setText(ingredient.getIngredient());
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detail_quantity)
        TextView ingredientQuantityTextView;
        @BindView(R.id.detail_measure)
        TextView ingredientMeasureTextView;
        @BindView(R.id.detail_type)
        TextView ingredientTypeTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}