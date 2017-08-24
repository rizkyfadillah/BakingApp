package com.rizkyfadillah.bakingapp.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * @author rizkyfadillah on 30/07/2017.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId")}, indices = {
        @Index(value = "recipeId")
})
public class Ingredient {
    @PrimaryKey
    public final String ingredientId;
    public final int recipeId;
    public final double quantity;
    public final String measure;
    public final String ingredient;

    public Ingredient(String ingredientId, int recipeId, double quantity, String measure, String ingredient) {
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Ingredient(Ingredient ingredient, String ingredientId, int recipeId) {
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.quantity = ingredient.quantity;
        this.measure = ingredient.measure;
        this.ingredient = ingredient.ingredient;
    }
}
