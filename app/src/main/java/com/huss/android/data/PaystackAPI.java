package com.huss.android.data;


import com.huss.android.models.Banks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface PaystackAPI {

    @GET("bank")
    Call<Banks> getBanks(@Query("gateway") String gateway, @Query("pay_with_bank") boolean payWithBank);


}