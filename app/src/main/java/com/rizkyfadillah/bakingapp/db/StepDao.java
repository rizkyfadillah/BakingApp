package com.rizkyfadillah.bakingapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.List;

/**
 * @author rizkyfadillah on 06/08/2017.
 */

//@Dao
public abstract class StepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSteps(List<Step> steps);

}
