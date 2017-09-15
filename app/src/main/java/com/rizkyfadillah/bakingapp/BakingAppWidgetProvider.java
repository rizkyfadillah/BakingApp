package com.rizkyfadillah.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragment;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.Timber;

public class BakingAppWidgetProvider extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String imageRes,
                                int recipeId, String recipeName, int appWidgetId, int [] appWidgetIds) {
        Timber.d("updateAppWidget");
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;
        if (width < 300) {
            rv = getSingleRecipeRemoteView(context, imageRes, recipeId, recipeName, appWidgetIds);
        } else {
            rv = getRecipeListRemoteView(context);
        }
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timber.d("onUpdate");
        RecipeListService.startUpdateRecipeListWidgets(context);
    }

    public static void updateRecipeListWidgets(Context context, AppWidgetManager appWidgetManager,
                                               String imgRes, int recipeId, String recipeName,  int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, imgRes, recipeId, recipeName, appWidgetId, appWidgetIds);
        }
    }

    private static RemoteViews getSingleRecipeRemoteView(Context context, String imgRes,
                                                         int recipeId, String recipeName,
                                                         int [] appWidgetIds) {
        Timber.d("getSingleRecipeRemoteView");
        Log.d(BakingAppWidgetProvider.class.getSimpleName(), "recipe name = " + recipeName);
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailFragment.EXTRA_RECIPE_ID, recipeId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        if (!imgRes.isEmpty()) {
            Picasso picasso = Picasso.with(context);
            picasso.load(imgRes)
                    .into(views, R.id.image, appWidgetIds);
        }
        views.setTextViewText(R.id.widget_recipe_name, String.valueOf(recipeName));

        views.setOnClickPendingIntent(R.id.widget_recipe_image, pendingIntent);
        return views;
    }

    private static RemoteViews getRecipeListRemoteView(Context context) {
        Timber.d("getRecipeListRemoteView");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return views;
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

