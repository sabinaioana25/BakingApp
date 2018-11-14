package com.example.android.bakingapp.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.Utils.NetworkUtils.createURL;
import static com.example.android.bakingapp.Utils.NetworkUtils.makeHttpRequest;

public class RecipeJsonUtils {

    private static final String LOG_TAG = RecipeJsonUtils.class.getSimpleName();

    // DetailActivity Main Components
    private static final String DETAIL_RECIPE_ID = "id";
    private static final String DETAIL_RECIPE_NAME = "name";
    private static final String DETAIL_RECIPE_SERVINGS = "servings";
    private static final String DETAIL_RECIPE_IMAGE = "image";

    // DetailActivity ingredients
    private static final String DETAIL_RECIPE_INGREDIENTS = "ingredients";
    private static final String DETAIL_RECIPE_INGREDIENT_QUANTITY = "quantity";
    private static final String DETAIL_RECIPE_INGREDIENT_MEASURE = "measure";
    private static final String DETAIL_RECIPE_INGREDIENT_TYPE = "ingredient";

    // DetailActivity steps
    private static final String DETAIL_RECIPE_STEPS = "steps";
    private static final String DETAIL_RECIPE_STEP_ID = "id";
    private static final String DETAIL_RECIPE_STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String DETAIL_RECIPE_STEP_DESCRIPTION = "description";
    private static final String DETAIL_RECIPE_STEP_VIDEO = "videoURL";
    private static final String DETAIL_RECIPE_STEP_THUMBNAIL = "thumbnailURL";

    // mandatory empty constructor
    private RecipeJsonUtils() {}

    public static List<Recipe> getRecipeData(String requestUrl) {
        URL url = createURL(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException ", e);
        }
        return extractFeaturesFromJson(jsonResponse);
    }

    public static List<Recipe> extractFeaturesFromJson(String jsonResponse) {

        int recipeID;
        String recipeName;
        String recipeTotalServings;
        String ingredientQuantity;
        String ingredientMeasure;
        String ingredientType;

        int stepID;
        String stepShortDescription;
        String stepDescription;

        String stepVideoUrl;

        String stepThumbnailUrl;
        String recipeImagePath;

        // empty List<Recipe>
        List<Recipe> recipeListMain = new ArrayList<>();
        List<Ingredient> ingredientsListMain = new ArrayList<>();
        List<Step> stepListMain = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // build the list of Recipe items
        try {
            JSONArray baseResponse = new JSONArray(jsonResponse);

            for (int i = 0; i < baseResponse.length(); i++) {
                JSONObject jsonObject = baseResponse.getJSONObject(i);
                recipeID = jsonObject.getInt(DETAIL_RECIPE_ID);
                recipeName = jsonObject.getString(DETAIL_RECIPE_NAME);
                recipeTotalServings = jsonObject.getString(DETAIL_RECIPE_SERVINGS);
                recipeImagePath = jsonObject.optString(DETAIL_RECIPE_IMAGE);
                //noinspection StatementWithEmptyBody
                if (recipeImagePath != null) {
                    Log.e(LOG_TAG, "No image to show");
                } else return null;

                // ingredients list
                JSONArray jsonArrayIngredients = jsonObject.optJSONArray(DETAIL_RECIPE_INGREDIENTS);
                if (jsonArrayIngredients.length() != 0) {
                    ingredientsListMain = new ArrayList<>();
                    for (int j = 0; j < jsonArrayIngredients.length(); j++) {

                        JSONObject jsonCurrentIngredientList = jsonArrayIngredients.getJSONObject
                                (j);
                        ingredientQuantity = jsonCurrentIngredientList.optString
                                (DETAIL_RECIPE_INGREDIENT_QUANTITY);
                        ingredientMeasure = jsonCurrentIngredientList.optString
                                (DETAIL_RECIPE_INGREDIENT_MEASURE);
                        ingredientType = jsonCurrentIngredientList.optString
                                (DETAIL_RECIPE_INGREDIENT_TYPE);

                        ingredientsListMain.add(new Ingredient(ingredientQuantity,
                                ingredientMeasure.toLowerCase(), ingredientType));
                    }
                }

                // step list
                JSONArray jsonArraySteps = jsonObject.optJSONArray(DETAIL_RECIPE_STEPS);
                if (jsonArraySteps.length() != 0) {
                    stepListMain = new ArrayList<>();
                    for (int k = 0; k < jsonArraySteps.length(); k++) {
                        JSONObject jsonCurrentStepList = jsonArraySteps.getJSONObject(k);
                        stepID = jsonCurrentStepList.optInt(DETAIL_RECIPE_STEP_ID);
                        stepShortDescription = jsonCurrentStepList.optString
                                (DETAIL_RECIPE_STEP_SHORT_DESCRIPTION);
                        stepDescription = jsonCurrentStepList.optString
                                (DETAIL_RECIPE_STEP_DESCRIPTION);
                        stepVideoUrl = jsonCurrentStepList.optString
                                (DETAIL_RECIPE_STEP_VIDEO);
                        stepThumbnailUrl = jsonCurrentStepList.optString
                                (DETAIL_RECIPE_STEP_THUMBNAIL);

                        stepListMain.add(new Step(stepID,
                                stepShortDescription, stepDescription,
                                stepVideoUrl, stepThumbnailUrl));
                    }
                }

                Recipe recipeMain = new Recipe(recipeID,
                        recipeName,
                        recipeTotalServings,
                        recipeImagePath,
                        ingredientsListMain,
                        stepListMain);

                recipeListMain.add(recipeMain);
            }
            return recipeListMain;
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}