package com.rizkyfadillah.bakingapp.di;

import android.app.Application;

import com.rizkyfadillah.bakingapp.BakingApp;
import com.rizkyfadillah.bakingapp.GridWidgetService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * @author rizkyfadillah on 28/07/2017.
 */
@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                ApiModule.class,
                ActivityBuilder.class,
                ServiceBuilder.class
        }
)
public interface AppComponent {

        @Component.Builder
        interface Builder {
                @BindsInstance
                Builder application(Application application);
                AppComponent build();
        }

        void inject(BakingApp app);

        void inject(GridWidgetService gridWidgetService);

}
