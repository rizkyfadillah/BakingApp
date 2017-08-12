package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * @author rizkyfadillah on 31/07/2017.
 */
@Module
public abstract class RecipeDetailFragmentProvider {

    @Binds
    @IntoMap
    @FragmentKey(RecipeDetailFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> provideDetailFragmentFactory(RecipeDetailFragmentComponent.Builder builder);

}
