package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.rizkyfadillah.bakingapp.repository.RecipeRepository;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.List;

import javax.inject.Inject;

/**
 * @author rizkyfadillah on 11/08/2017.
 */

public class RecipeDetailViewModel extends ViewModel {
    @VisibleForTesting
    private final MutableLiveData<Integer> recipeId = new MutableLiveData<>();

    private LiveData<Resource<Recipe>> recipe;

    @Inject
    public RecipeDetailViewModel(RecipeRepository recipeRepository) {
        recipe = Transformations.switchMap(recipeId, recipeRepository::getRecipeDetail);
    }

    public LiveData<Resource<Recipe>> getRecipe() {
        return recipe;
    }

    public void goGetRecipeDetail(int recipeId) {
        this.recipeId.postValue(recipeId);
    }

    public void retry() {
        int currentRecipeId = recipeId.getValue();
        recipeId.setValue(currentRecipeId);
    }
}
