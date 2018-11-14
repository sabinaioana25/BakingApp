package com.example.android.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.Activities.DetailSingleDessertActivity;
import com.example.android.bakingapp.R;

@SuppressWarnings("SameReturnValue")
public class BakingService extends IntentService {

    @SuppressWarnings("WeakerAccess")
    public static final String ACTION_ADD_RECIPE =
            "com.example.android.bakingapp.widget.action." +
                    "add_recipe";
    private static final String BAKING_APP_SERVICE_NAME = "BakingService";

    public BakingService() {
        super(BAKING_APP_SERVICE_NAME);
    }

    @SuppressWarnings("SameReturnValue")
    public static boolean startAddRecipes(Context context) {
        Intent intent = new Intent(context, BakingService.class);
        intent.setAction(ACTION_ADD_RECIPE);
        context.startService(intent);
        return true;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD_RECIPE.equals(action)) {
                handleAddIngredients();
            }
        }
    }

    private void handleAddIngredients() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                BakingAppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id
                .baking_app_widget_list_view);
        BakingAppWidget.updateIngredientWidgets(this, appWidgetManager,
                DetailSingleDessertActivity.recipeName, appWidgetIds);
    }
}
