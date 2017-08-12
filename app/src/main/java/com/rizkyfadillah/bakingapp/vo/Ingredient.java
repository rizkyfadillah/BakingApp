package com.rizkyfadillah.bakingapp.vo;

import com.google.gson.annotations.SerializedName;

/**
 * @author rizkyfadillah on 30/07/2017.
 */

public class Ingredient {

    public final double quantity;
    public final String measure;
    public final String ingredient;

    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

}
