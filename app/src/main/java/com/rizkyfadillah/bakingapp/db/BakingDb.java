package com.rizkyfadillah.bakingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Step;

/**
 * @author rizkyfadillah on 31/07/2017.
 */

@Database(entities = {Recipe.class, Step.class}, version = 1)
public abstract class BakingDb extends RoomDatabase {

    abstract public RecipeDao recipeDao();

//    abstract public StepDao stepDao();

}
