package com.rizkyfadillah.bakingapp.di;

import com.rizkyfadillah.bakingapp.MainActivity;
import com.rizkyfadillah.bakingapp.ui.recipelist.RecipeListFragmentProvider;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * @author rizkyfadillah on 28/07/2017.
 */
@Subcomponent(
        modules = {
                MainActivityModule.class,
                RecipeListFragmentProvider.class
        }
)
interface MainActivityComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {}

}
