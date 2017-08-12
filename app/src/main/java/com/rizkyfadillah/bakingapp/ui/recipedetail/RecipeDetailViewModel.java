package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.rizkyfadillah.bakingapp.repository.RecipeRepository;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author rizkyfadillah on 31/07/2017.
 */

public class RecipeDetailViewModel extends ViewModel {

    private Flowable<Resource<Recipe>> recipe;

    private RecipeRepository recipeRepository;

    @Inject
    RecipeDetailViewModel(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    Flowable<Resource<Recipe>> getDetailRecipe(int recipeId) {
        return recipeRepository.getDetailRecipe(recipeId);
    }

}
