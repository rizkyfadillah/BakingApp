package com.rizkyfadillah.bakingapp.util;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import com.rizkyfadillah.bakingapp.TestApp;

/**
 * @author rizkyfadillah on 07/09/2017.
 */

public class BakingAppTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, TestApp.class.getName(), context);
    }

}
