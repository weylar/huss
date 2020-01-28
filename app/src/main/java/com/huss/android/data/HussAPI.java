package com.huss.android.data;


import com.huss.android.models.AdImage;
import com.huss.android.models.Ads;
import com.huss.android.models.AllAds;
import com.huss.android.models.Category;
import com.huss.android.models.FavoriteAd;
import com.huss.android.models.Image;
import com.huss.android.models.Location;
import com.huss.android.models.Profile;
import com.huss.android.models.Report;
import com.huss.android.models.SingleAd;
import com.huss.android.models.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface HussAPI {

    /*Categories and subcategories*/
    @GET("category/all/getPopularCategories")
    Call<Category> getPopularCategory(@Header("Authorization") String name);

    @GET("category/all/categories")
    Call<Category> getAllCategory(@Header("Authorization") String name);

    @GET("subCategory/{categoryName}/getAllSubCategories")
    Call<SubCategory> getSubCategory(@Header("Authorization") String name,
                                     @Path("categoryName") String categoryName);

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


    /*Profile*/
    @GET("user/profile/{id}")
    Call<Profile> getUserProfile(@Header("Authorization") String name,
                                 @Path("id") String id);

    @PUT("user/profile")
    @FormUrlEncoded
    Call<Profile> updateUserProfile(@Header("Authorization") String name,
                                    @Field("profileImgUrl") String imageUrl,
                                    @Field("firstName") String firstName,
                                    @Field("lastName") String lastName,
                                    @Field("phoneNumber") String phone,
                                    @Field("city") String location);



    /*Ads*/
    @DELETE("ad/deleteAd/{id}")
    Call<Ads> deleteUserAd(@Header("Authorization") String token,
                           @Path("id") int id);

    @GET("photos")
    Call<AllAds> getSearchAds(@Query("key") String key);

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


    @POST("favorite/{adId}/create")
    Call<AllAds> favorite(@Header("Authorization") String token,
                          @Path("adId") String adId);

    @GET("favorite/{adId}/deleteFavorite")
    Call<AllAds> unFavorite(@Header("Authorization") String token,
                          @Path("adId") String adId);

    @GET("favorite")
    Call<FavoriteAd> getUserFavoritedAds(@Header("Authorization") String token);

    @GET("ad/getAllOwnAds")
    Call<AllAds> getUserAds(@Header("Authorization") String token);

    @GET("ad/getAllUserAds/{userId}")
    Call<AllAds> getUserAdsById(@Header("Authorization") String token,
                                @Path("userId") String id);

    @GET("ad/getAllAdsByLimit")
    Call<AllAds> getAllAdsByLimit(@Header("Authorization") String token);

    @GET("ad/getAllAds")
    Call<AllAds> getAllAds(@Header("Authorization") String token);

    @PUT("auth/updateOnlineStatus")
    @FormUrlEncoded
    Call<Profile> updateLastSeen(@Header("Authorization") String token,
                                 @Field("isOnline") boolean isOnline);


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


    @POST("adReport/create")
    @FormUrlEncoded
    Call<Report> reportAd(@Header("Authorization") String token,
                          @Field("productId") int productId,
                          @Field("userIAD") int userId,
                          @Field("reason") String reason);

    @POST("adReport/create")
    @FormUrlEncoded
    Call<Report> reportUser(@Header("Authorization") String token,
                          @Field("userIAD") int userId,
                          @Field("reason") String reason);
}