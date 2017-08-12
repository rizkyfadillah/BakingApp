package com.rizkyfadillah.bakingapp.vo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author rizkyfadillah on 28/07/2017.
 */

@Entity
public class Recipe {
    @PrimaryKey
    public final int id;
    public final String name;
    public final int servings;
    public final String image;

    @Ignore
    public List<Ingredient> ingredients;

    @Ignore
    public List<Step> steps;

    public Recipe(int id, String name, int servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

}
