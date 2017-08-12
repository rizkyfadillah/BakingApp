package com.rizkyfadillah.bakingapp.ui.recipedetail;

import com.rizkyfadillah.bakingapp.di.RecipeScope;
import com.rizkyfadillah.bakingapp.ui.recipelist.RecipeListFragment;
import com.rizkyfadillah.bakingapp.ui.recipelist.RecipeListFragmentModule;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * @author rizkyfadillah on 31/07/2017.
 */
@Subcomponent(modules = RecipeDetailFragmentModule.class)
public interface RecipeDetailFragmentComponent extends AndroidInjector<RecipeDetailFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<RecipeDetailFragment> {}

}
