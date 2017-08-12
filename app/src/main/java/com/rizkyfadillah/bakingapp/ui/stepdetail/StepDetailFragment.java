package com.rizkyfadillah.bakingapp.ui.stepdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rizkyfadillah.bakingapp.R;

/**
 * @author rizkyfadillah on 30/07/2017.
 */

public class StepDetailFragment extends Fragment {

    private TextView textStepDescription;

    private String stepDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.step_detail_fragment, container, false);

        textStepDescription = rootview.findViewById(R.id.text_step_description);

        textStepDescription.setText(stepDescription);

        return rootview;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

}
