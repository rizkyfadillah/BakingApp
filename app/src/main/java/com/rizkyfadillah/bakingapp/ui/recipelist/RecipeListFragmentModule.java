package com.rizkyfadillah.bakingapp.ui.recipelist;

import com.rizkyfadillah.bakingapp.api.Service;
import com.rizkyfadillah.bakingapp.db.RecipeDao;
import com.rizkyfadillah.bakingapp.di.RecipeScope;
import com.rizkyfadillah.bakingapp.repository.RecipeRepository;
import com.rizkyfadillah.bakingapp.repository.RecipeRepository2;

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

    @Provides
    RecipeListViewModel2 provideRecipeListViewModel2(RecipeRepository2 recipeRepository2) {
        return new RecipeListViewModel2(recipeRepository2);
    }

}
