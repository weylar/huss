package com.android.huss.data;


import com.android.huss.models.Ads;
import com.android.huss.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface HussAPI {

    @GET("photos")
    Call<List<Category>> getCategory();

    @GET("photos")
    Call<List<Ads>> getAds();

    @GET("photos/{id}")
    Call<Ads> getSingleAds(@Path("id") String id);

    @GET("photos")
    Call<List<Ads>> getSimilarAds(@Query("name") String adsName);

    @GET("photos")
    Call<List<Ads>> getFavoriteAds(@Query("userId") String userID);

}