package com.rizkyfadillah.bakingapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rizkyfadillah.bakingapp.ui.recipedetail.RecipeDetailFragment;
import com.rizkyfadillah.bakingapp.ui.stepdetail.StepDetailFragment;
import com.rizkyfadillah.bakingapp.vo.Step;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

/**
 * @author rizkyfadillah on 12/08/2017.
 */

public class StepDetailActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, RecipeDetailFragment.OnStepClickListener {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private Toolbar toolbar;

    private boolean mTwoPane;

    private String stepDescription;
    private String stepVideoUrl;
    private int recipeId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Bundle bundle = getIntent().getExtras();

        if (savedInstanceState != null) {
            stepDescription = savedInstanceState.getString(StepDetailFragment.STEP_DESCRIPTION);
            stepVideoUrl = savedInstanceState.getString(StepDetailFragment.STEP_VIDEO_URL);
            recipeId = savedInstanceState.getInt("recipe_id", -1);
        } else {
            if (bundle != null) {
                stepDescription = bundle.getString(StepDetailFragment.STEP_DESCRIPTION);
                stepVideoUrl = bundle.getString(StepDetailFragment.STEP_VIDEO_URL);
                recipeId = bundle.getInt("recipe_id");
            }
        }

        if (findViewById(R.id.toolbar) != null) {
            toolbar = findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Step");
                getSupportActionBar().setHomeButtonEnabled(true);
            }
        }

        if (findViewById(R.id.step_list_fragment) != null) {
            Timber.d("two pane");

            mTwoPane = true;

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            Bundle newBundle = new Bundle();
            newBundle.putString(StepDetailFragment.STEP_DESCRIPTION, stepDescription);
            newBundle.putString(StepDetailFragment.STEP_VIDEO_URL, stepVideoUrl);
            stepDetailFragment.setArguments(newBundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_container, stepDetailFragment)
                    .commit();
        } else {
            Timber.d("one pane");

            mTwoPane = false;

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            Bundle newBundle = new Bundle();
            newBundle.putString(StepDetailFragment.STEP_DESCRIPTION, stepDescription);
            newBundle.putString(StepDetailFragment.STEP_VIDEO_URL, stepVideoUrl);
            stepDetailFragment.setArguments(newBundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_container, stepDetailFragment)
                    .commit();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onStepSelected(int position, Step step) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(StepDetailFragment.STEP_DESCRIPTION, stepDescription);
        outState.putString(StepDetailFragment.STEP_VIDEO_URL, stepVideoUrl);

        super.onSaveInstanceState(outState, outPersistentState);
    }
}
