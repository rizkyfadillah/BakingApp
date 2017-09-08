package com.rizkyfadillah.bakingapp.ui.recipelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.rizkyfadillah.bakingapp.repository.RecipeRepository;
import com.rizkyfadillah.bakingapp.util.AbsentLiveData;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * @author rizkyfadillah on 11/08/2017.
 */

public class RecipeListViewModel extends ViewModel {

    private final MutableLiveData<Boolean> goGetRecipes;

    private LiveData<Resource<List<Recipe>>> recipes;

    @Inject
    RecipeListViewModel(RecipeRepository recipeRepository) {
        this.goGetRecipes = new MutableLiveData<>();
        recipes = Transformations.switchMap(goGetRecipes, input -> {
            if (input) {
                return recipeRepository.getRecipes();
            }
            return AbsentLiveData.create();
        });
    }

    LiveData<Resource<List<Recipe>>> getRecipes() {
        return recipes;
    }

    void recipesNeeded() {
        goGetRecipes.setValue(true);
    }

    public void retry() {
        recipesNeeded();
    }
}
