package com.rizkyfadillah.bakingapp.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author rizkyfadillah on 29/07/2017.
 */

public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("imageUrl")
    public static void bindImage(ImageView imageView, String url) {
        if (url != null) {
            if (!url.isEmpty()) {
                Picasso.with(imageView.getContext())
                        .load(url).into(imageView);
            }
        }
    }

}
