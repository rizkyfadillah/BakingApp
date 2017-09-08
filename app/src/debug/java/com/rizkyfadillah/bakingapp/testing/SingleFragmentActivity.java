package com.rizkyfadillah.bakingapp.testing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author rizkyfadillah on 07/09/2017.
 */

public class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FrameLayout content = new FrameLayout(this);
//        content.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        content.setId(R.id.container);
//        setContentView(content);
    }

//    public void setFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction()
////                .add(R.id.container, fragment, "TEST")
//                .commit();
//    }

//    public void replaceFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, fragment).commit();
//    }
}

