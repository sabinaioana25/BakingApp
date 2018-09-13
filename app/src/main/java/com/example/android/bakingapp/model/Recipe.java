package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    public static String TAG = Recipe.class.getSimpleName();
    // pojos
    private int recipeId;
    private String name;
    private String servings;
    private String image;

    // lists
    private List<Ingredient> ingredients;
    private List<Step> steps;

    // constructor
    public Recipe(int recipeId, String name, String servings, String image, List<Ingredient>
            ingredients, List<Step> steps) {
        this.recipeId = recipeId;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    protected Recipe(Parcel parcel) {
        recipeId = parcel.readInt();
        name = parcel.readString();
        servings = parcel.readString();
        image = parcel.readString();
        ingredients = new ArrayList<>();
        parcel.readList(ingredients, Ingredient.class.getClassLoader());
        steps = new ArrayList<>();
        parcel.readList(steps, Step.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    // getter methods
    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeString(image);
        dest.writeList(ingredients);
//        dest.writeList(steps);
    }
}
