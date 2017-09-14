package com.rizkyfadillah.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rizkyfadillah.bakingapp.api.Service;
import com.rizkyfadillah.bakingapp.di.DaggerAppComponent;
import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragment;
import com.rizkyfadillah.bakingapp.vo.Recipe;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author rizkyfadillah on 9/14/2017.
 */

public class GridWidgetService extends RemoteViewsService {

    @Inject
    Service service;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder().application(getApplication())
                .build().inject(this);
    }

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(getApplicationContext(), service);
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    private List<Recipe> recipes;

    private Service service;

    GridRemoteViewsFactory(Context applicationContext, Service service) {
        mContext = applicationContext;
        this.service = service;
    }

    @Override
    public void onCreate() {
        Timber.d("GridWidgetService");
    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        Timber.d("onDataSetChanged");
        try {
            recipes = service.getRecipes2().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
        int recipeId = recipes.get(position).id;
        String recipeName = recipes.get(position).name;
        String imgRes = recipes.get(position).image;

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);

        // Update the plant image
//        views.setImageViewResource(R.id.widget_recipe_image, imgRes);
        views.setTextViewText(R.id.widget_recipe_name, String.valueOf(recipeName));

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
        Bundle extras = new Bundle();
        extras.putInt(RecipeDetailFragment.EXTRA_RECIPE_ID, recipeId);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_recipe_image, fillInIntent);
        views.setOnClickFillInIntent(R.id.widget_recipe_name, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}


