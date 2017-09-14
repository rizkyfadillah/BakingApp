package com.rizkyfadillah.bakingapp.di;

import com.rizkyfadillah.bakingapp.GridWidgetService;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * @author rizkyfadillah on 9/14/2017.
 */

@Subcomponent
public interface GridWidgetServiceComponent extends AndroidInjector<GridWidgetService> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<GridWidgetService> {}

}
