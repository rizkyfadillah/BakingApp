package com.rizkyfadillah.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.rizkyfadillah.bakingapp.ui.stepdetail.StepDetailFragment;

/**
 * @author rizkyfadillah on 12/08/2017.
 */

public class StepDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();

        StepDetailFragment stepDetailFragment = new StepDetailFragment();

        fragmentManager.beginTransaction()
                .add(R.id.step_container, stepDetailFragment)
                .commit();
    }
}
