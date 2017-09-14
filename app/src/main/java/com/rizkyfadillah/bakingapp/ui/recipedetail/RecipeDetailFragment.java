package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.databinding.RecipeDetailFragmentBinding;
import com.rizkyfadillah.bakingapp.di.Injectable;
import com.rizkyfadillah.bakingapp.vo.Ingredient;
import com.rizkyfadillah.bakingapp.vo.Status;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

/**
 * @author rizkyfadillah on 29/07/2017.
 */

public class RecipeDetailFragment extends Fragment implements
        LifecycleRegistryOwner, Injectable,
        RecipeStepAdapter.OnStepClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static final String EXTRA_RECIPE_ID = "recipe_id";
    public static final String IS_TWO_PANE = "is_two_pane";

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private RecipeDetailFragmentBinding binding;

    private RecipeDetailViewModel recipeDetailViewModel;

    private RecipeStepAdapter recipeStepAdapter;
    private RecipeIngredientAdapter recipeIngredientAdapter;

    private List<Step> steps = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();

    private OnStepClickListener mCallback;

    private boolean isTwoPane;

    private int recipeId;

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    public interface OnStepClickListener {
        void onStepSelected(int position, Step step);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        isTwoPane = args.getBoolean(IS_TWO_PANE, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recipe_detail_fragment, container, false);

        binding.setRetryCallback(() -> recipeDetailViewModel.retry());

        recipeStepAdapter = new RecipeStepAdapter(this, steps);
        recipeIngredientAdapter = new RecipeIngredientAdapter(ingredients);

        binding.recyclerviewStep.setAdapter(recipeStepAdapter);
        binding.recyclerviewStep.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        binding.recyclerviewIngredient.setAdapter(recipeIngredientAdapter);
        binding.recyclerviewIngredient.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recipeDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeDetailViewModel.class);

        recipeId = getActivity().getIntent().getIntExtra(EXTRA_RECIPE_ID, 0);

        recipeDetailViewModel.goGetRecipeDetail(recipeId);

        showDetailRecipe();
    }

    private void showDetailRecipe() {
        recipeDetailViewModel.getRecipe()
                .observe(this, recipeResource -> {
                    binding.setRecipe(recipeResource == null ? null : recipeResource.data);
                    binding.setRecipeDetailResource(recipeResource);
                    if (recipeResource != null) {
                        if (recipeResource.status == Status.SUCCESS ||
                                recipeResource.status == Status.LOADING) {
                            if (recipeResource.data != null) {
                                steps.clear();
                                ingredients.clear();
                                steps.addAll(recipeResource.data.steps);
                                ingredients.addAll(recipeResource.data.ingredients);
                                recipeStepAdapter.notifyDataSetChanged();
                                if (isTwoPane) {
                                    recipeStepAdapter.selectFirstItem();
                                }
                                recipeIngredientAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    binding.executePendingBindings();
                });
    }

    public List<Step> getSteps() {
        return recipeStepAdapter.getSteps();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onClickStep(int position, Step step) {
        mCallback.onStepSelected(position, step);
    }

}
