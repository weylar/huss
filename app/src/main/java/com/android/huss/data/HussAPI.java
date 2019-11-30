package com.android.huss.data;


import com.android.huss.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface HussAPI {

    @GET("api")
    Call<List<Category>> getCategory(@Query("access_key") String key);

}