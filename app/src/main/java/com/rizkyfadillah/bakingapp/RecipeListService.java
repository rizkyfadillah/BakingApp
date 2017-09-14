package com.rizkyfadillah.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.rizkyfadillah.bakingapp.api.ApiResponse;
import com.rizkyfadillah.bakingapp.api.Service;
import com.rizkyfadillah.bakingapp.vo.Recipe;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * @author rizkyfadillah on 9/14/2017.
 */

public class RecipeListService extends DaggerIntentService {

    @Inject
    Service service;

    private List<Recipe> recipes;

    public RecipeListService() {
        super("RecipeListService");
    }

//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        handleGetRecipes();
//        return null;
//    }

    public RecipeListService(String name) {
        super(name);
    }

    public static void startUpdateRecipeListWidgets(Context context) {
        Timber.d("startUpdateRecipeListWidgets");
        Intent intent = new Intent(context, RecipeListService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Timber.d("onHandleIntent");
        if (intent != null) {
            try {
                handleGetRecipes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleGetRecipes() throws IOException {
        Timber.d("handleGetRecipes");
        List<Recipe> response = service.getRecipes2().execute().body();
        if (response != null) {
            Timber.d("recipes not null");
            recipes = response;

            int recipeId = recipes.get(0).id;
            String recipeName = recipes.get(0).name;
            String imgRes = recipes.get(0).image;
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(RecipeListService.this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(RecipeListService.this, BakingAppWidgetProvider.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
            BakingAppWidgetProvider.updateRecipeListWidgets(RecipeListService.this, appWidgetManager, imgRes,
                    recipeId, recipeName, appWidgetIds);
        }

    }

}
