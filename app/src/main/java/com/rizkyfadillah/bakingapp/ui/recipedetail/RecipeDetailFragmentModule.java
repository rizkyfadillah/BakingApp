package com.rizkyfadillah.bakingapp.ui.recipedetail;

import com.rizkyfadillah.bakingapp.repository.RecipeRepository;
import com.rizkyfadillah.bakingapp.repository.RecipeRepository2;

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

    @Provides
    RecipeDetailViewModel2 provideRecipeDetailViewModel2(RecipeRepository2 recipeRepository2) {
        return new RecipeDetailViewModel2(recipeRepository2);
    }

}
