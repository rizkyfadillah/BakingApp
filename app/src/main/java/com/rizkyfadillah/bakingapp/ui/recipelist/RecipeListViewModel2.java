package com.rizkyfadillah.bakingapp.ui.recipelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.rizkyfadillah.bakingapp.RetryCallback;
import com.rizkyfadillah.bakingapp.repository.RecipeRepository2;
import com.rizkyfadillah.bakingapp.util.AbsentLiveData;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author rizkyfadillah on 11/08/2017.
 */

public class RecipeListViewModel2 extends ViewModel {

    private final MutableLiveData<Boolean> goGetRecipes;

    private LiveData<Resource<List<Recipe>>> recipes;

    @Inject
    RecipeListViewModel2(RecipeRepository2 recipeRepository2) {
        this.goGetRecipes = new MutableLiveData<>();
        recipes = Transformations.switchMap(goGetRecipes, input -> {
            if (input) {
                return recipeRepository2.getRecipes();
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
