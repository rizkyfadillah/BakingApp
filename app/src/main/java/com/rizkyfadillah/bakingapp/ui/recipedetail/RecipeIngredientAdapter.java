package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.vo.Ingredient;

import java.util.List;

/**
 * @author rizkyfadillah on 19/08/2017.
 */

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.IngredientViewHolder> {

    private final List<Ingredient> ingredients;

    private Context context;

    RecipeIngredientAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, viewGroup, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder ingredientViewHolder, int position) {
        ingredientViewHolder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView textIngredient;
        private TextView textQuantityMeasure;

        IngredientViewHolder(View itemView) {
            super(itemView);

            textIngredient = itemView.findViewById(R.id.text_ingredient);
            textQuantityMeasure = itemView.findViewById(R.id.text_quantity_measure);
        }

        void bind(Ingredient ingredient) {
            textIngredient.setText(ingredient.ingredient);
            textQuantityMeasure.setText(ingredient.quantity + " " + ingredient.measure);
        }
    }

}
