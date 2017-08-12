package com.rizkyfadillah.bakingapp.util;

import android.arch.lifecycle.LiveData;

/**
 * @author rizkyfadillah on 30/07/2017.
 */

public class AbsentLiveData extends LiveData {

    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }

}
