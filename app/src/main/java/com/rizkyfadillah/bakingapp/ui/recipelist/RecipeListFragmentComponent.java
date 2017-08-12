package com.rizkyfadillah.bakingapp.ui.recipelist;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * @author rizkyfadillah on 28/07/2017.
 */
@Subcomponent(modules = RecipeListFragmentModule.class)
public interface RecipeListFragmentComponent extends AndroidInjector<RecipeListFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<RecipeListFragment> {}

}
