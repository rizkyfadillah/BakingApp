package com.rizkyfadillah.bakingapp;

import android.content.Intent;
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
import timber.log.Timber;

/**
 * @author rizkyfadillah on 30/07/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, RecipeDetailFragment.OnStepClickListener,
        FragmentManager.OnBackStackChangedListener {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private FragmentManager fragmentManager;

    private boolean mTwoPane;

    private String title;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail2);

        title = getIntent().getStringExtra("recipe_name");
        id = getIntent().getIntExtra("recipe_id", 0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(title);
        }

        fragmentManager = getSupportFragmentManager();

        fragmentManager.addOnBackStackChangedListener(this);

        if (findViewById(R.id.step_container) != null) {
            mTwoPane = true;

            StepDetailFragment stepDetailFragment = new StepDetailFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, stepDetailFragment, "Step Detail")
                    .setReorderingAllowed(true)
                    .commit();
        } else {
            mTwoPane = false;

            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, recipeDetailFragment, "Recipe Detail Fragment")
                    .setReorderingAllowed(true)
                    .commit();
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

            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, stepDetailFragment)
                    .commit();
        } else {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(StepDetailFragment.STEP_DESCRIPTION, step.description);
            bundle.putString(StepDetailFragment.STEP_VIDEO_URL, step.videoURL);
            stepDetailFragment.setArguments(bundle);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, stepDetailFragment, "Step Detail Fragment")
                    .addToBackStack(null)
                    .commit();

//            Intent intent = new Intent(this, StepDetailActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(StepDetailFragment.STEP_DESCRIPTION, step.description);
//            bundle.putString(StepDetailFragment.STEP_VIDEO_URL, step.videoURL);
//            bundle.putInt("recipe_id", id);
//            intent.putExtras(bundle);
//            startActivity(intent);
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (fragmentManager.getBackStackEntryCount() > 0) {
//            Timber.d("masuk sini 1");
//            fragmentManager.popBackStack();
//        } else {
//            Timber.d("masuk sini 2");
//            fragmentManager.popBackStack();
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressed() {
        if (mTwoPane) {
            NavUtils.navigateUpFromSameTask(this);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackStackChanged() {
        Timber.d("\n");
        Timber.d(" Current status of the backstack: \n");

        int count = fragmentManager.getBackStackEntryCount();
        for (int i=count-1; i>=0; i--){
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            Timber.d(" "+entry.getName()+" \n");
        }
        Timber.d("\n");
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

}
