package com.android.huss.data;


import com.android.huss.models.AdImage;
import com.android.huss.models.Ads;
import com.android.huss.models.Category;
import com.android.huss.models.Location;
import com.android.huss.models.Profile;
import com.android.huss.models.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    @GET("photos")
    Call<List<SubCategory>> getSubCategory(@Query("catName") String catName);

    @GET("locations")
    Call<List<Location>> getLocation();


    @POST("ad/{categoryName}/{subCategoryName}create")
    Call<Integer> createAd(@Body Ads ad,
                          @Path("categoryName") String categoryName,
                          @Path("subCategoryName") String subCategoryName,
                          @Header("Authorization") String name
    );

    @POST("images")
    Call<String> createImage(@Body AdImage ad);

}