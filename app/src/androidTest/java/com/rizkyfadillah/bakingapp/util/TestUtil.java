package com.rizkyfadillah.bakingapp.util;

import com.rizkyfadillah.bakingapp.vo.Ingredient;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rizkyfadillah on 9/11/2017.
 */

public class TestUtil {

    public static Recipe createRecipe(int recipeId, String name, int servings, String image) {
        Recipe recipe = new Recipe(recipeId, name, servings, image);
        recipe.steps = createSteps(recipeId);
        recipe.ingredients = createIngredients(recipeId);
        return recipe;
    }

    public static List<Ingredient> createIngredients(int recipeId) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("1", recipeId, 2, "CUP", "Pandan"));
        return ingredients;
    }

    public static List<Step> createSteps(int recipeId) {
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("1", 1, recipeId, "Aduk adonan", "hahahaha", "", ""));
        return steps;
    }
}
