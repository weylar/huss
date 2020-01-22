package com.android.huss.data;


import com.android.huss.models.Ad;
import com.android.huss.models.AdImage;
import com.android.huss.models.Ads;
import com.android.huss.models.AllAds;
import com.android.huss.models.Category;
import com.android.huss.models.Image;
import com.android.huss.models.Location;
import com.android.huss.models.Profile;
import com.android.huss.models.SingleAd;
import com.android.huss.models.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface HussAPI {

    @GET("category/getPopularCategories/{limit}")
    Call<Category> getPopularCategory(@Header("Authorization") String name,
                                      @Path("limit") int limit);

    @GET("category/all/categories")
    Call<Category> getAllCategory(@Header("Authorization") String name);

    @GET("subCategory/{categoryName}/getAllSubCategories")
    Call<SubCategory> getSubCategory(@Header("Authorization") String name,
                                     @Path("categoryName") String categoryName);


    @GET("photos/{id}")
    Call<Profile> getUserProfile(@Path("id") String id);

    @GET("photos")
    Call<List<Ads>> getAds();

    @PUT("user/profile")
    @FormUrlEncoded
    Call<Profile> updateUserProfile(@Header("Authorization") String name,
                                    @Field("profileImgUrl") String imageUrl,
                                    @Field("firstName") String firstName,
                                    @Field("lastName") String lastName,
                                    @Field("phone") String phone,
                                    @Field("city") String location);


    @POST("ad/{categoryName}/{subCategoryName}/create")
    @FormUrlEncoded
    Call<Ads> postAd(@Path("categoryName") String categoryName,
                     @Path("subCategoryName") String subCategoryName,
                     @Field("title") String title,
                     @Field("description") String description,
                     @Field("price") String price,
                     @Field("location") String location,
                     @Field("isNegotiable") Boolean isNegotiable,
                     @Header("Authorization") String name);


    @DELETE("ad/deleteAd/{id}")
    Call<Ads> deleteUserAd(@Header("Authorization") String token,
                          @Path("id") int id);

    @GET("photos")
    Call<List<Ads>> getSearchAds(@Query("key") String key);

    @GET("ad/getAd/{id}")
    Call<SingleAd> getSingleAds(@Header("Authorization") String token,
                                @Path("id") String id);

    @PUT("ad/editAd/{id}")
    @FormUrlEncoded
    Call<SingleAd> updateAd(@Header("Authorization") String token,
                            @Path("id") String id,
                            @Field("categoryName") String categoryName,
                            @Field("subCategoryName") String subCategoryName,
                            @Field("title") String title,
                            @Field("description") String description,
                            @Field("price") String price,
                            @Field("location") String location,
                            @Field("isNegotiable") Boolean isNegotiable);

    @GET("photos")
    Call<List<Ads>> getSimilarAds(@Query("name") String adsName);

    @GET("photos")
    Call<List<Ads>> getFavoriteAds(@Query("userId") String userID);

    @GET("ad/getAllOwnAds")
    Call<AllAds> getUserAds(@Header("Authorization") String token);


    @GET("locations")
    Call<List<Location>> getLocation();


    @POST("adImage/{adId}/create")
    @FormUrlEncoded
    Call<Image> createImage(@Path("adId") int adId,
                            @Field("imageUrl") String url,
                            @Field("displayImage") Boolean isFeatured,
                            @Header("Authorization") String token);


    @GET("adImage/{adId}/getAnAdImages")
    Call<AdImage> getFeaturedImage(@Path("adId") int adId,
                                   @Header("Authorization") String token);

    @POST("auth/login")
    @FormUrlEncoded
    Call<Profile> login(@Field("email") String email,
                        @Field("password") String password);

    @POST("auth/signup")
    @FormUrlEncoded
    Call<Profile> signUp(@Field("firstName") String firstName,
                         @Field("lastName") String lastName,
                         @Field("email") String email,
                         @Field("password") String password,
                         @Field("confirmPassword") String confirmPassword);

    @GET("user/{email}")
    Call<Profile> forgotPassword(@Path("email") String email);

    @PUT("user/new_password/{id}/{token}")
    @FormUrlEncoded
    Call<Profile> resetPassword(@Path("id") String email,
                                @Path("token") String token,
                                @Field("password") String password,
                                @Field("confirmPassword") String confirmPassword);

}