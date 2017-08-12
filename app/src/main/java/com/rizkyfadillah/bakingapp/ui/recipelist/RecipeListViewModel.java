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

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

/**
 * @author rizkyfadillah on 29/07/2017.
 */

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository recipeRepository;

    @Inject
    RecipeListViewModel(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    Flowable<Resource<List<Recipe>>> getRecipes() {
        return recipeRepository.getRecipes();
    }

}
