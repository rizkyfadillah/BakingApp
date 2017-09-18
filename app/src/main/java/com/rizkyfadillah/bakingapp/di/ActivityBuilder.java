package com.rizkyfadillah.bakingapp.di;

import android.app.Activity;

import com.rizkyfadillah.bakingapp.RecipeListActivity;
import com.rizkyfadillah.bakingapp.RecipeDetailActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * @author rizkyfadillah on 28/07/2017.
 */
@Module
abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(RecipeListActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(RecipeDetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindRecipeDetailActivity(RecipeDetailActivityComponent.Builder builder);

}
