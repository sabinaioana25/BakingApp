package com.example.android.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Fragments.FragmentDetailSingleDessert;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class BakingAppRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingAppWidgetItemFactory(getApplicationContext(), intent);
    }

    class BakingAppWidgetItemFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context;
        private int appWidgetId;
        private List<Recipe> recipeNameListWidget;
        private List<Ingredient> ingredientListWidget;

        BakingAppWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            // connect to a data source
        }

        @Override
        public void onDataSetChanged() {
            ingredientListWidget = FragmentDetailSingleDessert.ingredients;
        }

        @Override
        public void onDestroy() {
            // close connection to data source
        }

        @Override
        public int getCount() {
            if (ingredientListWidget == null) return 0;
            return ingredientListWidget.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.baking_app_widget);
            views.setTextViewText(
                    R.id.appwidget_text_ingredient_name, ingredientListWidget.get
                    (position).getIngredient());
            views.setTextViewText(
                    R.id.appwidget_text_quantity, ingredientListWidget.get
                    (position).getQuantity());
            views.setTextViewText(
                    R.id.appwidget_text_measure, ingredientListWidget.get(position).getMeasure());
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}