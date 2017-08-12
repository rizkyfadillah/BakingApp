package com.rizkyfadillah.bakingapp.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailViewModel;
import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailViewModel2;
import com.rizkyfadillah.bakingapp.ui.recipelist.RecipeListViewModel;
import com.rizkyfadillah.bakingapp.ui.recipelist.RecipeListViewModel2;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author rizkyfadillah on 30/07/2017.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel.class)
    abstract ViewModel bindRecipeListViewModel(RecipeListViewModel recipeListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel2.class)
    abstract ViewModel bindRecipeListViewModel2(RecipeListViewModel2 recipeListViewModel2);

    @Binds
    @IntoMap
    @ViewModelKey(RecipeDetailViewModel.class)
    abstract ViewModel bindRecipeDetailViewModel(RecipeDetailViewModel recipeDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecipeDetailViewModel2.class)
    abstract ViewModel bindRecipeDetailViewMode2l(RecipeDetailViewModel2 recipeDetailViewModel2);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(CustomViewModelFactory factory);

}
