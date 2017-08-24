package com.rizkyfadillah.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

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
        implements HasSupportFragmentInjector, RecipeDetailFragment.OnStepClickListener {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private boolean mTwoPane;

    private String title;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        title = getIntent().getStringExtra("recipe_name");
        id = getIntent().getIntExtra("recipe_id", 0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(title);
        }

        if (findViewById(R.id.step_container) != null) {
            mTwoPane = true;

            StepDetailFragment stepDetailFragment = new StepDetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_container, stepDetailFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onStepSelected(int position, Step step) {
        if (mTwoPane) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(StepDetailFragment.STEP_DESCRIPTION, step.description);
            bundle.putString(StepDetailFragment.STEP_VIDEO_URL, step.videoURL);
            stepDetailFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, stepDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(StepDetailFragment.STEP_DESCRIPTION, step.description);
            bundle.putString(StepDetailFragment.STEP_VIDEO_URL, step.videoURL);
            bundle.putInt("recipe_id", id);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
