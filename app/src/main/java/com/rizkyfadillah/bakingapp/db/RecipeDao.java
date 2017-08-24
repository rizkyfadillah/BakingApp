package com.rizkyfadillah.bakingapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.rizkyfadillah.bakingapp.vo.Ingredient;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author rizkyfadillah on 31/07/2017.
 */

@Dao
public abstract class RecipeDao {

    @Query("SELECT * FROM recipe WHERE id = :id")
    public abstract LiveData<Recipe> loadRecipe(int id);

    @Query("SELECT * FROM recipe WHERE id = :id")
    public abstract Flowable<Recipe> loadRecipe2(int id);

    @Query("SELECT * FROM recipe")
    public abstract LiveData<List<Recipe>> loadRecipes();

    @Query("SELECT * FROM recipe")
    public abstract Flowable<List<Recipe>> loadRecipes2();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRecipe(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRecipes(List<Recipe> recipes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertStep(Step step);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSteps(List<Step> steps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertIngredients(List<Ingredient> ingredients);

    @Query("SELECT * FROM step WHERE recipeId = :recipeId")
    public abstract LiveData<List<Step>> loadSteps(int recipeId);

    @Query("SELECT * FROM ingredient WHERE recipeId = :recipeId")
    public abstract LiveData<List<Ingredient>> loadIngredients(int recipeId);

    @Query("SELECT * FROM step WHERE recipeId = :recipeId")
    public abstract Flowable<List<Step>> loadSteps2(int recipeId);

}
