package com.rizkyfadillah.bakingapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rizkyfadillah.bakingapp.api.ApiResponse;
import com.rizkyfadillah.bakingapp.api.Service;
import com.rizkyfadillah.bakingapp.db.BakingDb;
import com.rizkyfadillah.bakingapp.db.RecipeDao;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author rizkyfadillah on 11/08/2017.
 */

@Singleton
public class RecipeRepository2 {

    private final MediatorLiveData<Resource<List<Recipe>>> result = new MediatorLiveData<>();

    private Service service;
    private RecipeDao recipeDao;
    private BakingDb db;

    public RecipeRepository2(Service service, RecipeDao recipeDao, BakingDb db) {
        this.service = service;
        this.recipeDao = recipeDao;
        this.db = db;
    }

    public LiveData<Resource<List<Recipe>>> getRecipes() {
        return new NetworkBoundResource<List<Recipe>, List<Recipe>>() {
            @Override
            protected void saveCallResult(@NonNull List<Recipe> recipes) {
                for (Recipe recipe : recipes) {
                    List<Step> steps = new ArrayList<>();
                    for (Step step : recipe.steps) {
                        Timber.d(step.description);
                        String stepId = Integer.toString(recipe.id) + Integer.toString(step.id);
                        Step newStep = new Step(step, stepId, recipe.id);
                        steps.add(newStep);
                    }
                    db.beginTransaction();
                    try {
                        recipeDao.insertRecipe(recipe);
                        recipeDao.insertSteps(steps);
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Recipe> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Recipe>> loadFromDb() {
                return recipeDao.loadRecipes();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Recipe>>> createCall() {
                return service.getRecipes2();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Step>>> getRecipeSteps(int recipeId) {
        return new NetworkBoundResource<List<Step>, List<Step>>() {
            @Override
            protected void saveCallResult(@NonNull List<Step> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Step> data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<Step>> loadFromDb() {
                return recipeDao.loadSteps(recipeId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Step>>> createCall() {
                return null;
            }
        }.asLiveData();
    }

}
