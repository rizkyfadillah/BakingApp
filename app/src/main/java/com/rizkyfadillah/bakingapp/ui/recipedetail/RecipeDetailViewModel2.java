package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.rizkyfadillah.bakingapp.repository.RecipeRepository;
import com.rizkyfadillah.bakingapp.repository.RecipeRepository2;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.List;

import javax.inject.Inject;

/**
 * @author rizkyfadillah on 11/08/2017.
 */

public class RecipeDetailViewModel2 extends ViewModel {

    private final MutableLiveData<Integer> recipeId = new MutableLiveData<>();

    private LiveData<Resource<List<Step>>> steps;

    private LiveData<Resource<Recipe>> recipe;

    @Inject
    RecipeDetailViewModel2(RecipeRepository2 recipeRepository) {
        steps = Transformations.switchMap(recipeId, recipeRepository::getRecipeSteps);
        recipe = Transformations.switchMap(recipeId, recipeRepository::getRecipeDetail);
    }

    public LiveData<Resource<List<Step>>> getSteps() {
        return steps;
    }

    public LiveData<Resource<Recipe>> getRecipe() {
        return recipe;
    }

    void goGetRecipeDetail(int recipeId) {
        this.recipeId.postValue(recipeId);
    }

}
