package com.rizkyfadillah.bakingapp.repository;

import com.rizkyfadillah.bakingapp.api.Service;
import com.rizkyfadillah.bakingapp.db.BakingDb;
import com.rizkyfadillah.bakingapp.db.RecipeDao;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author rizkyfadillah on 29/07/2017.
 */

@Singleton
public class RecipeRepository {

    private Service service;
    private RecipeDao recipeDao;
    private BakingDb db;

    public RecipeRepository(Service service, RecipeDao recipeDao, BakingDb db) {
        this.service = service;
        this.recipeDao = recipeDao;
        this.db = db;
    }

    public Flowable<Resource<List<Recipe>>> getRecipes() {
        return service.getRecipes()
                .map(Resource::success)
                .map(resource -> {
                    saveRecipes(resource.data);
                    return resource.data;
                })
                .map(Resource::success)
                .onErrorReturn(throwable -> Resource.error(throwable.getMessage(), null))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(Resource.loading(null));
    }

    private void saveRecipes(List<Recipe> recipes) {
        db.beginTransaction();
        try {
            for (Recipe recipe : recipes) {
                Timber.d(recipe.name);
                List<Step> steps = new ArrayList<>();
                for (Step step : recipe.steps) {
                    String stepId = Integer.toString(recipe.id) + Integer.toString(step.id);
                    Step newStep = new Step(step, stepId, recipe.id);
                    steps.add(newStep);
                }
                recipeDao.insertRecipe(recipe);
                recipeDao.insertSteps(steps);
            }
        } finally {
            db.endTransaction();
        }
    }

    public Flowable<Resource<Recipe>> getDetailRecipe(int recipeId) {
        return Flowable.zip(recipeDao.loadRecipe2(recipeId), recipeDao.loadSteps2(recipeId),
                (recipe, steps) -> {
                    recipe.steps = steps;
                    return recipe;
                })
                .map(Resource::success)
                .onErrorReturn(throwable -> Resource.error(throwable.getMessage(), null))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(Resource.loading(null));
    }
}
