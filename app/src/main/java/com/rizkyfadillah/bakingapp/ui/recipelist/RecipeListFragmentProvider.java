package com.rizkyfadillah.bakingapp.ui.recipelist;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * @author rizkyfadillah on 29/07/2017.
 */
@Module
public abstract class RecipeListFragmentProvider {

    @Binds
    @IntoMap
    @FragmentKey(RecipeListFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> provideListFragmentFactory(RecipeListFragmentComponent.Builder builder);

}
