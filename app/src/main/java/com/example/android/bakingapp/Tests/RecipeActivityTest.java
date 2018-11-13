package com.example.android.bakingapp.Tests;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activities.DetailSingleDessertActivity;
import com.example.android.bakingapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    public Parcelable recipe;
    public String RECIPE_NAME = DetailSingleDessertActivity.recipeName;
    Bundle bundle;
    private static final String NUTELLA_PIE = "Nutella Pie";

    @Rule
    public final ActivityTestRule<DetailSingleDessertActivity> recipeActivityTestRule =
            new ActivityTestRule<>(DetailSingleDessertActivity.class);

    @Test
    public void CheckRecipeName() {
        Intent intent = new Intent();
        bundle.putParcelable("recipe", recipe);
        intent.putExtras(bundle);
        recipeActivityTestRule.launchActivity(intent);

        Espresso.onView(withId(R.id.text_view_dessert_name)).check(matches(withText(NUTELLA_PIE)));

    }

}









