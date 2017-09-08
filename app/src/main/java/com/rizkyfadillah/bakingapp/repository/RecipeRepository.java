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
import com.rizkyfadillah.bakingapp.vo.Ingredient;
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
public class RecipeRepository {

    private final MediatorLiveData<Resource<List<Recipe>>> result = new MediatorLiveData<>();

    private Service service;
    private RecipeDao recipeDao;
    private BakingDb db;

    public RecipeRepository(Service service, RecipeDao recipeDao, BakingDb db) {
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
                    List<Ingredient> ingredients = new ArrayList<>();
                    for (int i=0; i<recipe.ingredients.size(); i++) {
                        String ingredientId = Integer.toString(recipe.id) + Integer.toString(i);
                        Ingredient ingredient = recipe.ingredients.get(i);
                        Ingredient newIngredient = new Ingredient(ingredient, ingredientId, recipe.id);
                        ingredients.add(newIngredient);
                    }
                    for (Step step : recipe.steps) {
                        String stepId = Integer.toString(recipe.id) + Integer.toString(step.id);
                        Step newStep = new Step(step, stepId, recipe.id);
                        steps.add(newStep);
                    }
                    db.beginTransaction();
                    try {
                        recipeDao.insertRecipe(recipe);
                        recipeDao.insertIngredients(ingredients);
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
                return service.getRecipes();
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

    public LiveData<Resource<Recipe>> getRecipeDetail(int recipeId) {
        return new NetworkBoundResource<Recipe, Recipe>() {
            @Override
            protected void saveCallResult(@NonNull Recipe item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable Recipe data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Recipe> loadFromDb() {
                return Transformations.switchMap(recipeDao.loadRecipe(recipeId),
                        recipe -> {
                            MediatorLiveData<Recipe> newRecipe = new MediatorLiveData<>();
                            LiveData<List<Step>> stepsDbResource = recipeDao.loadSteps(recipeId);
                            newRecipe.addSource(recipeDao.loadSteps(recipeId),
                                    steps -> {
                                        newRecipe.removeSource(stepsDbResource);
                                        recipe.steps = steps;
                                        LiveData<List<Ingredient>> ingredientsDbResource = recipeDao.loadIngredients(recipeId);
                                        newRecipe.addSource(ingredientsDbResource,
                                                ingredients -> {
                                                    newRecipe.removeSource(ingredientsDbResource);
                                                    recipe.ingredients = ingredients;
                                                    newRecipe.setValue(recipe);
                                                });
                                    });
                            return newRecipe;
                        });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Recipe>> createCall() {
                return null;
            }
        }.asLiveData();
    }
}
