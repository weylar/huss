package com.android.huss.data;


import com.android.huss.models.Ads;
import com.android.huss.models.Category;
import com.android.huss.models.Profile;
import com.android.huss.models.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface HussAPI {

    @GET("photos")
    Call<List<Category>> getCategory();

    @GET("photos/{id}")
    Call<Profile> getUserProfile(@Path("id") String id);

    @GET("photos")
    Call<List<Ads>> getAds();

    @GET("photos/{id}")
    Call<Ads> getSingleAds(@Path("id") String id);

    @GET("photos")
    Call<List<Ads>> getSimilarAds(@Query("name") String adsName);

    @GET("photos")
    Call<List<Ads>> getFavoriteAds(@Query("userId") String userID);

    @GET("photos")
    Call<List<Ads>> getUserAds(@Query("userId") String userID);

    @GET("photos/{catName}")
    Call<List<SubCategory>> getSubCategory(@Path("catName") String catName);

}