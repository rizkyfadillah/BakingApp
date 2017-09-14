package com.rizkyfadillah.bakingapp.di;

import android.app.Activity;

import com.rizkyfadillah.bakingapp.RecipeListActivity;
import com.rizkyfadillah.bakingapp.RecipeDetailActivity;
import com.rizkyfadillah.bakingapp.RecipeListService;
import com.rizkyfadillah.bakingapp.StepDetailActivity;
import com.rizkyfadillah.bakingapp.api.Service;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ServiceKey;
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

    @Binds
    @IntoMap
    @ActivityKey(StepDetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindStepDetailActivity(StepDetailActivityComponent.Builder builder);

}
