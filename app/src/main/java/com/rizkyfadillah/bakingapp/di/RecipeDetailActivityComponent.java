package com.rizkyfadillah.bakingapp.di;

import com.rizkyfadillah.bakingapp.MainActivity;
import com.rizkyfadillah.bakingapp.RecipeDetailActivity;
import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragmentProvider;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * @author rizkyfadillah on 31/07/2017.
 */
@Subcomponent(
        modules = {
                RecipeDetailActivityModule.class,
                RecipeDetailFragmentProvider.class
        }
)
public interface RecipeDetailActivityComponent extends AndroidInjector<RecipeDetailActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<RecipeDetailActivity> {}

}
