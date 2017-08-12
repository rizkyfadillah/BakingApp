package com.rizkyfadillah.bakingapp.di;

import com.rizkyfadillah.bakingapp.Constants;
import com.rizkyfadillah.bakingapp.api.Service;
import com.rizkyfadillah.bakingapp.util.LiveDataCallAdapter;
import com.rizkyfadillah.bakingapp.util.LiveDataCallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author rizkyfadillah on 28/07/2017.
 */
@Module
class ApiModule {

    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    Retrofit provideRetrofitAdapter(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    Service provideService(Retrofit retrofit) {
        return retrofit.create(Service.class);
    }

}
