package com.rizkyfadillah.bakingapp.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rizkyfadillah.bakingapp.api.Service;
import com.rizkyfadillah.bakingapp.db.BakingDb;
import com.rizkyfadillah.bakingapp.db.RecipeDao;
import com.rizkyfadillah.bakingapp.repository.RecipeRepository;
import com.rizkyfadillah.bakingapp.repository.RecipeRepository2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author rizkyfadillah on 28/07/2017.
 */
@Module(
        subcomponents = {
                MainActivityComponent.class,
                RecipeDetailActivityComponent.class
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
    RecipeRepository provideRecipeRepository(Service service, RecipeDao recipeDao, BakingDb db) {
        return new RecipeRepository(service, recipeDao, db);
    }

    @Singleton
    @Provides
    RecipeRepository2 provideRecipeRepository2(Service service, RecipeDao recipeDao, BakingDb db) {
        return new RecipeRepository2(service, recipeDao, db);
    }

}
