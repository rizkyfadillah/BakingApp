package com.rizkyfadillah.bakingapp.ui.recipelist;

import com.rizkyfadillah.bakingapp.repository.RecipeRepository;

import dagger.Module;
import dagger.Provides;

/**
 * @author rizkyfadillah on 28/07/2017.
 */
@Module
public class RecipeListFragmentModule {

    @Provides
    RecipeListViewModel provideRecipeListViewModel(RecipeRepository recipeRepository) {
        return new RecipeListViewModel(recipeRepository);
    }

}
