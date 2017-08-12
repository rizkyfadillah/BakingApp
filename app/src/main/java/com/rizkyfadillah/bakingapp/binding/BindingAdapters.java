package com.rizkyfadillah.bakingapp.binding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * @author rizkyfadillah on 29/07/2017.
 */

public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
