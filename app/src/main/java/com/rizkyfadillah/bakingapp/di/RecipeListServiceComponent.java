package com.rizkyfadillah.bakingapp.di;

import com.rizkyfadillah.bakingapp.RecipeListService;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * @author rizkyfadillah on 9/14/2017.
 */

@Subcomponent
public interface RecipeListServiceComponent extends AndroidInjector<RecipeListService> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<RecipeListService> {}

}
