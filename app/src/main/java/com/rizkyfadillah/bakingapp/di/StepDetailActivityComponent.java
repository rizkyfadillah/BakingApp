package com.rizkyfadillah.bakingapp.di;

import com.rizkyfadillah.bakingapp.StepDetailActivity;
import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragmentProvider;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * @author rizkyfadillah on 24/08/2017.
 */
@Subcomponent(
        modules = {
                StepDetailActivityModule.class,
                RecipeDetailFragmentProvider.class
        }
)
public interface StepDetailActivityComponent extends AndroidInjector<StepDetailActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<StepDetailActivity> {}

}
