package com.rizkyfadillah.bakingapp.di;

import com.rizkyfadillah.bakingapp.GridWidgetService;
import com.rizkyfadillah.bakingapp.RecipeListService;
import com.rizkyfadillah.bakingapp.api.Service;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.ServiceKey;
import dagger.multibindings.IntoMap;

/**
 * @author rizkyfadillah on 9/14/2017.
 */

@Module
abstract class ServiceBuilder {

    @Binds
    @IntoMap
    @ServiceKey(RecipeListService.class)
    abstract AndroidInjector.Factory<? extends android.app.Service> bindRecipeListService(RecipeListServiceComponent.Builder builder);

    @Binds
    @IntoMap
    @ServiceKey(GridWidgetService.class)
    abstract AndroidInjector.Factory<? extends android.app.Service> bindGridWidgetService(GridWidgetServiceComponent.Builder builder);

}
