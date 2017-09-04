package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.StepDetailActivity;
import com.rizkyfadillah.bakingapp.databinding.RecipeDetailFragmentBinding;
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

public class RecipeDetailFragment extends LifecycleFragment implements RecipeStepAdapter.OnStepClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    private RecipeDetailFragmentBinding binding;

    //    private RecipeDetailViewModel recipeDetailViewModel;
    private RecipeDetailViewModel2 recipeDetailViewModel2;

    private RecipeStepAdapter recipeStepAdapter;
    private RecipeIngredientAdapter recipeIngredientAdapter;

    private List<Step> steps = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();

    private OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position, Step step);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recipe_detail_fragment, container, false);

        Timber.d("RecipeDetailFragment");

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

//        recipeDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
//                .get(RecipeDetailViewModel.class);
        recipeDetailViewModel2 = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeDetailViewModel2.class);

        int recipeId = getActivity().getIntent().getIntExtra(EXTRA_RECIPE_ID, 0);

        Timber.d(String.valueOf(recipeId));

        recipeDetailViewModel2.goGetRecipeDetail(recipeId);

//        showDetailRecipe2();
        showDetailRecipe3();
//        showDetailRecipe(recipeId);
    }

    private void showDetailRecipe3() {
        Timber.d("showDetailRecipe3");
        recipeDetailViewModel2.getRecipe()
                .observe(this, recipeResource -> {
                    binding.setRecipe(recipeResource == null ? null : recipeResource.data);
                    binding.setRecipeDetailResource(recipeResource);
                    if (recipeResource != null) {
                        if (recipeResource.status == Status.SUCCESS) {
                            if (recipeResource.data != null) {
                                Timber.d(recipeResource.data.steps.size()+"");
                                Timber.d(recipeResource.data.ingredients.size()+"");
                                steps.clear();
                                ingredients.clear();
                                steps.addAll(recipeResource.data.steps);
                                ingredients.addAll(recipeResource.data.ingredients);
                                recipeStepAdapter.notifyDataSetChanged();
                                recipeIngredientAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

//    private void showDetailRecipe2() {
//        recipeDetailViewModel2.getSteps()
//                .observe(this, recipeResource -> {
//                    binding.setRecipeDetailResource(recipeResource);
//                    if (recipeResource != null) {
//                        if (recipeResource.status == Status.SUCCESS) {
//                            if (recipeResource.data != null) {
//                                Timber.d(recipeResource.data.size()+"");
//                                steps.clear();
//                                steps.addAll(recipeResource.data);
//                                recipeStepAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                });
//    }

//    private void showDetailRecipe(int recipeId) {
//        recipeDetailViewModel.getDetailRecipe(recipeId)
//                .subscribe(recipeResource -> {
//                    binding.setRecipeDetailResource(recipeResource);
//                    if (recipeResource != null) {
//                        if (recipeResource.status == Status.SUCCESS) {
//                            if (recipeResource.data != null) {
//                                Timber.d(recipeResource.data.steps.size()+"");
//                                steps.clear();
//                                steps.addAll(recipeResource.data.steps);
//                                recipeStepAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                });
//    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onClickStep(int position, Step step) {
        mCallback.onStepSelected(position, step);
    }

}
