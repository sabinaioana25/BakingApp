package com.example.android.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @SuppressWarnings("WeakerAccess")
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String recipeName,
                                int appWidgetId) {

        RemoteViews views = getIngredientsRemoteListView(context);
        views.setTextViewText(R.id.baking_app_widget_text, recipeName);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingService.startAddRecipes(context);
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager
            appWidgetManager, String recipeName, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName, appWidgetId);
        }
    }

    private static RemoteViews getIngredientsRemoteListView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout
                .baking_app_widget_list_view);

        // Set the ListWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, BakingAppRemoteViewsService.class);
        views.setRemoteAdapter(R.id.baking_app_widget_list_view, intent);

        return views;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

