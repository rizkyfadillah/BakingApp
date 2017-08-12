package com.rizkyfadillah.bakingapp.ui.recipelist;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.RecipeDetailActivity;
import com.rizkyfadillah.bakingapp.databinding.RecipeListFragmentBinding;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

/**
 * @author rizkyfadillah on 28/07/2017.
 */

public class RecipeListFragment extends LifecycleFragment implements RecipeAdapter.OnRecipeClickListener {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private final String EXTRA_RECIPE_ID = "recipe_id";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    //    private RecipeListViewModel recipeListViewModel;
    private RecipeListViewModel2 recipeListViewModel2;

    private List<Recipe> recipes = new ArrayList<>();

    private RecipeAdapter recipeAdapter;

    private RecipeListFragmentBinding binding;

    public RecipeListFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recipe_list_fragment, container, false);

        binding.setRetryCallback(() -> recipeListViewModel2.retry());

        recipeAdapter = new RecipeAdapter(this, recipes);
        binding.recyclerviewRecipe.setAdapter(recipeAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        recipeListViewModel = ViewModelProviders.of(this, viewModelFactory)
//                .get(RecipeListViewModel.class);
        recipeListViewModel2 = ViewModelProviders.of(this, viewModelFactory)
                .get(RecipeListViewModel2.class);

        recipeListViewModel2.recipesNeeded();

        showRecipes();
    }

    private void showRecipes() {
        recipeListViewModel2.getRecipes()
                .observe(this, recipeResource -> {
                            binding.setRecipeResource(recipeResource);
                            if (recipeResource.status != Status.ERROR) {
                                if (recipeResource.data != null) {
                                    recipes.clear();
                                    recipes.addAll(recipeResource.data);
                                    recipeAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                );
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onClickRecipe(int position) {
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipes.get(position).id);
        startActivity(intent);
    }

}
