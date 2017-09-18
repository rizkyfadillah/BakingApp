package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.vo.Ingredient;
import com.rizkyfadillah.bakingapp.vo.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author rizkyfadillah on 31/07/2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder> {
    private OnStepClickListener callback;

    private List<Step> steps;

    private int selectedItem = 0;

    private Context context;

    RecipeStepAdapter(OnStepClickListener callback, List<Step> steps) {
        this.callback = callback;
        this.steps = steps;
    }

    @Override
    public RecipeStepAdapter.StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_step, viewGroup, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepAdapter.StepViewHolder stepViewHolder, int position) {
        Step step = steps.get(position);
        stepViewHolder.bind(step, position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public List<Step> getSteps() {
        return steps;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textShortDescription;
        private ImageView imageStep;
        private CardView cardStep;

        private Step step;

        private int position;

        StepViewHolder(View itemView) {
            super(itemView);

            cardStep = itemView.findViewById(R.id.card_step);
            textShortDescription = itemView.findViewById(R.id.text_short_description);
            imageStep = itemView.findViewById(R.id.image_step);

            itemView.setOnClickListener(this);
        }

        void bind(Step step, int position) {
            this.position = position;
            this.step = step;
            textShortDescription.setText(step.shortDescription);
            if (!step.thumbnailURL.isEmpty()) {
                Picasso.with(context)
                        .load(step.thumbnailURL)
                        .into(imageStep);
            }
            if (selectedItem == position) {
                cardStep.setCardBackgroundColor(Color.parseColor("#cccccc"));
            } else {
                cardStep.setCardBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

        @Override
        public void onClick(View view) {
            selectedItem = position;
            notifyDataSetChanged();
            callback.onClickStep(position, step);
        }
    }

    void selectFirstItem() {
        callback.onClickStep(0, steps.get(0));
    }

    void select(int position) {
        callback.onClickStep(position, steps.get(position));
    }

    interface OnStepClickListener {
        void onClickStep(int position, Step step);
    }

}
