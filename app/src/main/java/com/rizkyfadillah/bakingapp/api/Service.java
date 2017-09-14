package com.rizkyfadillah.bakingapp.api;

import android.arch.lifecycle.LiveData;

import com.rizkyfadillah.bakingapp.vo.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author rizkyfadillah on 28/07/2017.
 */

public interface Service {

    @GET("topher/2017/May/59121517_baking/baking.json")
    LiveData<ApiResponse<List<Recipe>>> getRecipes();

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes2();

}
