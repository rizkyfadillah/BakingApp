package com.rizkyfadillah.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragment;
import com.rizkyfadillah.bakingapp.ui.stepdetail.StepDetailFragment;
import com.rizkyfadillah.bakingapp.vo.Step;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author rizkyfadillah on 30/07/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity
        implements HasSupportFragmentInjector,
        RecipeDetailFragment.OnStepClickListener, StepDetailFragment.OnNavigationClickListener {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private final String STATE_LAST_ACTIVE_FRAGMENT = "state_last_active_fragment";

    private final String STATE_STEP_DESCRIPTION = "state_step_description";
    private final String STATE_STEP_VIDEO_URL = "state_step_video_url";

    private FragmentManager fragmentManager;

    private boolean mTwoPane;

    private int currrentStepPosition;

    private RecipeDetailFragment recipeDetailFragment;
    private StepDetailFragment stepDetailFragment;

    private String activeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        String title = getIntent().getStringExtra("recipe_name");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(title);
        }

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.step_container) != null) {
            mTwoPane = true;
            Step step = null;
            if (savedInstanceState != null) {
                if (savedInstanceState.getString(STATE_LAST_ACTIVE_FRAGMENT)
                        .equals("Step Detail Fragment")) {
                    activeFragment = "Step Detail Fragment";
                } else if (savedInstanceState.getString(STATE_LAST_ACTIVE_FRAGMENT)
                        .equals("Recipe Detail Fragment")) {
                    activeFragment = "Recipe Detail Fragment";
                }
                String stepDescription = savedInstanceState.getString(STATE_STEP_DESCRIPTION);
                String stepVideoUrl = savedInstanceState.getString(STATE_STEP_VIDEO_URL);
                step = new Step(stepDescription, stepVideoUrl);
            }
            openStepDetail(step, R.id.step_container);
        } else {
            mTwoPane = false;

            if (savedInstanceState != null) {
                if (savedInstanceState.getString(STATE_LAST_ACTIVE_FRAGMENT) != null) {
                    if (savedInstanceState.getString(STATE_LAST_ACTIVE_FRAGMENT)
                            .equals("Step Detail Fragment")) {
                        openRecipeDetail();

                        activeFragment = "Step Detail Fragment";
                        String stepDescription = savedInstanceState.getString(STATE_STEP_DESCRIPTION);
                        String stepVideoUrl = savedInstanceState.getString(STATE_STEP_VIDEO_URL);
                        Step step = new Step(stepDescription, stepVideoUrl);
                        openStepDetail(step, R.id.fragment_container);
                    } else {
                        activeFragment = "Recipe Detail Fragment";
                        openRecipeDetail();
                    }
                } else {
                    activeFragment = "Recipe Detail Fragment";
                    openRecipeDetail();
                }
            } else {
                activeFragment = "Recipe Detail Fragment";
                openRecipeDetail();
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onStepSelected(int position, Step step) {
        if (mTwoPane) {
            openStepDetail(step, R.id.step_container);
        } else {
            activeFragment = "Step Detail Fragment";
            openStepDetail(step, R.id.fragment_container);
        }
    }

    private void openStepDetail(Step step, int container) {
        stepDetailFragment = new StepDetailFragment();

        if (step != null) {
            Bundle bundle = new Bundle();
            bundle.putString(StepDetailFragment.STEP_DESCRIPTION, step.description);
            bundle.putString(StepDetailFragment.STEP_VIDEO_URL, step.videoURL);
            stepDetailFragment.setArguments(bundle);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(container, stepDetailFragment, "Step Detail Fragment")
                .addToBackStack(null)
                .commit();
    }

    private void openRecipeDetail() {
        recipeDetailFragment = new RecipeDetailFragment();

        Bundle bundle = new Bundle();
        if (mTwoPane) {
            bundle.putBoolean(RecipeDetailFragment.IS_TWO_PANE, true);
        } else {
            bundle.putBoolean(RecipeDetailFragment.IS_TWO_PANE, false);
        }
        recipeDetailFragment.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, recipeDetailFragment, "Recipe Detail Fragment")
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mTwoPane || getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                instanceof RecipeDetailFragment) {
            NavUtils.navigateUpFromSameTask(this);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (mTwoPane || getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                        instanceof RecipeDetailFragment) {
                    NavUtils.navigateUpFromSameTask(this);
                } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                        instanceof StepDetailFragment) {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickNext() {
        if (currrentStepPosition != recipeDetailFragment.getSteps().size()-1) {
            openStepDetail(recipeDetailFragment.getSteps().get(currrentStepPosition+1), R.id.fragment_container);
            currrentStepPosition = currrentStepPosition + 1;
        }
    }

    @Override
    public void onClickPrevious() {
        if (currrentStepPosition != 0) {
            openStepDetail(recipeDetailFragment.getSteps().get(currrentStepPosition-1), R.id.fragment_container);
            currrentStepPosition = currrentStepPosition - 1;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_LAST_ACTIVE_FRAGMENT, activeFragment);
        if (activeFragment != null) {
            if (activeFragment.equals("Step Detail Fragment")) {
                outState.putString(STATE_STEP_DESCRIPTION, stepDetailFragment.getStepDescription());
                outState.putString(STATE_STEP_VIDEO_URL, stepDetailFragment.getStepVideoUrl());
            }
        }
        super.onSaveInstanceState(outState);
    }
}
