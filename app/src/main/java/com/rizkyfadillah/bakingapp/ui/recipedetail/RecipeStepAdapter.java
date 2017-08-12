package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.List;

import timber.log.Timber;

/**
 * @author rizkyfadillah on 31/07/2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder> {

    private OnStepClickListener callback;

    private List<Step> steps;

    private Context context;

    RecipeStepAdapter(OnStepClickListener callback, List<Step> steps) {
        this.callback = callback;
        this.steps = steps;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_step, viewGroup, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder stepViewHolder, int i) {
        stepViewHolder.bind(steps.get(i), i);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int position;

        private TextView textDescription;

        StepViewHolder(View itemView) {
            super(itemView);

            textDescription = (TextView) itemView.findViewById(R.id.text_description);

            itemView.setOnClickListener(this);
        }

        void bind(Step step, int position) {
            Timber.d(steps.get(position).description);
            this.position = position;
            textDescription.setText(step.description);
        }

        @Override
        public void onClick(View view) {
            callback.onClickStep(position);
        }

    }

    interface OnStepClickListener {
        void onClickStep(int position);
    }

}
