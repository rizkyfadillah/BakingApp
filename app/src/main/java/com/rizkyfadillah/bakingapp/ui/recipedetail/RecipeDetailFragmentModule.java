package com.rizkyfadillah.bakingapp.ui.recipedetail;

import com.rizkyfadillah.bakingapp.repository.RecipeRepository;

import dagger.Module;
import dagger.Provides;

/**
 * @author rizkyfadillah on 31/07/2017.
 */
@Module
public class RecipeDetailFragmentModule {

    @Provides
    RecipeDetailViewModel provideRecipeDetailViewModel(RecipeRepository recipeRepository) {
        return new RecipeDetailViewModel(recipeRepository);
    }

}
