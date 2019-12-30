package com.android.huss.data;


import com.android.huss.models.Ads;
import com.android.huss.models.Banks;
import com.android.huss.models.Category;
import com.android.huss.models.Profile;
import com.android.huss.models.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface PaystackAPI {

    @GET("bank")
    Call<Banks> getBanks(@Query("gateway") String gateway, @Query("pay_with_bank") boolean payWithBank);


}