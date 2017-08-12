package com.rizkyfadillah.bakingapp.ui.recipelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author rizkyfadillah on 29/07/2017.
 */

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    private Context context;

    private OnRecipeClickListener callback;

    RecipeAdapter(OnRecipeClickListener callback, List<Recipe> recipes) {
        this.callback = callback;
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textRecipe;
        private ImageView imageRecipe;

        private int position;

        RecipeViewHolder(View itemView) {
            super(itemView);
            textRecipe = itemView.findViewById(R.id.text_recipe);
            imageRecipe = itemView.findViewById(R.id.imageview_recipe);

            itemView.setOnClickListener(this);
        }

        void bind(Recipe recipe, int position) {
            this.position = position;
            textRecipe.setText(recipe.name);
            if (!recipe.image.isEmpty()) {
                Picasso.with(context)
                        .load(recipe.image)
                        .into(imageRecipe);
            }
        }

        @Override
        public void onClick(View view) {
            callback.onClickRecipe(position);
        }
    }

    interface OnRecipeClickListener {
        void onClickRecipe(int position);
    }

}
