package com.rizkyfadillah.bakingapp.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.rizkyfadillah.bakingapp.AppExecutors;
import com.rizkyfadillah.bakingapp.RecipeListService;
import com.rizkyfadillah.bakingapp.api.Service;
import com.rizkyfadillah.bakingapp.db.BakingDb;
import com.rizkyfadillah.bakingapp.db.RecipeDao;
import com.rizkyfadillah.bakingapp.repository.RecipeRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author rizkyfadillah on 28/07/2017.
 */
@Module(
        subcomponents = {
                MainActivityComponent.class,
                RecipeDetailActivityComponent.class,
                StepDetailActivityComponent.class,
                RecipeListServiceComponent.class,
                GridWidgetServiceComponent.class
        },
        includes = ViewModelModule.class
)
class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    BakingDb provideBakingDb(Application application) {
        return Room.databaseBuilder(application, BakingDb.class, "baking.db").build();
    }

    @Provides
    @Singleton
    RecipeDao recipeDao(BakingDb bakingDb) {
        return bakingDb.recipeDao();
    }

    @Singleton
    @Provides
    RecipeRepository provideRecipeRepository(AppExecutors appExecutors, Service service, RecipeDao recipeDao, BakingDb db) {
        return new RecipeRepository(appExecutors, service, recipeDao, db);
    }

}
