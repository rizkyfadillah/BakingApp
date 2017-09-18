package com.rizkyfadillah.bakingapp.ui.recipelist;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.RecipeDetailActivity;
import com.rizkyfadillah.bakingapp.databinding.RecipeListFragmentBinding;
import com.rizkyfadillah.bakingapp.di.Injectable;
import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragment;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author rizkyfadillah on 28/07/2017.
 */

public class RecipeListFragment extends Fragment implements
        LifecycleRegistryOwner, Injectable,
        RecipeAdapter.OnRecipeClickListener {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private static final String STATE_SCROLL_POSITION = "state_scroll_position";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private RecipeListViewModel recipeListViewModel;

    private List<Recipe> recipes = new ArrayList<>();

    private RecipeAdapter recipeAdapter;

    private int scrollPosition;

    private RecipeListFragmentBinding binding;

    public RecipeListFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recipe_list_fragment, container, false);

        binding.setRetryCallback(() -> recipeListViewModel.retry());

        recipeAdapter = new RecipeAdapter(this, recipes);
        binding.recyclerviewRecipe.setAdapter(recipeAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL_POSITION);
        }

        recipeListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeListViewModel.class);

        recipeListViewModel.recipesNeeded();

        showRecipes();
    }

    private void showRecipes() {
        recipeListViewModel.getRecipes()
                .observe(this, recipesResource -> {
                            binding.setRecipeResource(recipesResource);
                            if (recipesResource.status != Status.ERROR) {
                                if (recipesResource.data != null) {
                                    recipes.clear();
                                    recipes.addAll(recipesResource.data);
                                    recipeAdapter.notifyDataSetChanged();
                                    binding.recyclerviewRecipe.smoothScrollToPosition(scrollPosition);
                                }
                            }
                        }
                );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClickRecipe(int position) {
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailFragment.EXTRA_RECIPE_ID, recipes.get(position).id);
        intent.putExtra(RecipeDetailFragment.EXTRA_RECIPE_NAME, recipes.get(position).name);
        startActivity(intent);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SCROLL_POSITION,
                ((LinearLayoutManager) binding.recyclerviewRecipe.getLayoutManager())
                        .findLastCompletelyVisibleItemPosition());

        super.onSaveInstanceState(outState);
    }
}
