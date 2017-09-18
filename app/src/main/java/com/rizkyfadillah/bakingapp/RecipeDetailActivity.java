package com.rizkyfadillah.bakingapp;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragment;
import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailViewModel;
import com.rizkyfadillah.bakingapp.ui.stepdetail.StepDetailFragment;
import com.rizkyfadillah.bakingapp.vo.Status;
import com.rizkyfadillah.bakingapp.vo.Step;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

import static com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragment.EXTRA_RECIPE_ID;

/**
 * @author rizkyfadillah on 30/07/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity implements
        LifecycleRegistryOwner,
        HasSupportFragmentInjector,
        FragmentManager.OnBackStackChangedListener,
        RecipeDetailFragment.OnStepClickListener,
        StepDetailFragment.OnNavigationClickListener {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private RecipeDetailViewModel recipeDetailViewModel;

//    private final String STATE_LAST_ACTIVE_FRAGMENT = "state_last_active_fragment";
//    private final String STATE_STEP_ID = "state_step_id";
//    private final String STATE_STEP_DESCRIPTION = "state_step_description";
//    private final String STATE_STEP_VIDEO_URL = "state_step_video_url";
//    private final String STATE_STEP_POSITION = "state_step_position";

//    private FragmentManager fragmentManager;

    private boolean mTwoPane;

    private int currrentStepPosition;

    private RecipeDetailFragment recipeDetailFragment;
//    private StepDetailFragment stepDetailFragment;

    private String activeFragment;

    private List<Step> steps = new ArrayList<>();

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

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (findViewById(R.id.fragment_container) != null) {
            mTwoPane = false;
            if (savedInstanceState != null) {
                return;
            }
            openRecipeDetail();
        } else {
            mTwoPane = true;
        }
    }

//    private Step getStepFromSavedInstance(Bundle savedInstanceState) {
//        int stepId = savedInstanceState.getInt(STATE_STEP_ID);
//        String stepDescription = savedInstanceState.getString(STATE_STEP_DESCRIPTION);
//        String stepVideoUrl = savedInstanceState.getString(STATE_STEP_VIDEO_URL);
//        return new Step(stepId, stepDescription, stepVideoUrl);
//    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onStepSelected(int position, Step step) {
        StepDetailFragment stepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.step_detail_fragment);

        if (stepDetailFragment != null && mTwoPane) {
            stepDetailFragment.setStep(step);
        } else {
            stepDetailFragment = StepDetailFragment.createInstance(step.id, step.description, step.videoURL);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, stepDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void openStepDetail(Step step, int container) {
        StepDetailFragment stepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                .findFragmentByTag(StepDetailFragment.TAG);

        if (step != null) {
            if (stepDetailFragment == null) {
                stepDetailFragment = StepDetailFragment.createInstance(step.id, step.description, step.videoURL);
            } else {
                stepDetailFragment.setStep(step);
            }
        } else {
            stepDetailFragment = StepDetailFragment.createInstance(0, null, null);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, stepDetailFragment, StepDetailFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void openRecipeDetail() {
        recipeDetailFragment = RecipeDetailFragment.createInstance(mTwoPane);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, recipeDetailFragment, RecipeDetailFragment.TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
//        if (mTwoPane || getSupportFragmentManager().findFragmentById(R.id.fragment_container)
//                instanceof RecipeDetailFragment) {
//            NavUtils.navigateUpFromSameTask(this);
//        } else if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)
//                instanceof StepDetailFragment) {
//            activeFragment = RecipeDetailFragment.TAG;
//            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        } else {
            super.onBackPressed();
//        }
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
                    activeFragment = RecipeDetailFragment.TAG;
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
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

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString(STATE_LAST_ACTIVE_FRAGMENT, activeFragment);
//        if (activeFragment != null) {
//            if (activeFragment.equals(StepDetailFragment.TAG)) {
//                outState.putInt(STATE_STEP_ID, stepDetailFragment.getStepId());
//                outState.putString(STATE_STEP_DESCRIPTION, stepDetailFragment.getStepDescription());
//                outState.putString(STATE_STEP_VIDEO_URL, stepDetailFragment.getStepVideoUrl());
//                outState.putInt(STATE_STEP_POSITION, currrentStepPosition);
//            }
//        }
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onBackStackChanged() {
        Timber.d("\n");
        Timber.d(" Current status of the backstack: \n");
        int count = getSupportFragmentManager().getBackStackEntryCount();
        for (int i=count-1; i>=0; i--){
            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(i);
            Timber.d(" "+entry.getName()+" \n");
        }
    }
}