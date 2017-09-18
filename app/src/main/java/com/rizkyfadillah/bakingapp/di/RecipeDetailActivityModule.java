package com.rizkyfadillah.bakingapp.di;

import com.rizkyfadillah.bakingapp.repository.RecipeRepository;
import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragmentComponent;
import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author rizkyfadillah on 31/07/2017.
 */
@Module(subcomponents = RecipeDetailFragmentComponent.class)
class RecipeDetailActivityModule {

    @Provides
    RecipeDetailViewModel provideRecipeDetailViewModel(RecipeRepository recipeRepository) {
        return new RecipeDetailViewModel(recipeRepository);
    }

}
