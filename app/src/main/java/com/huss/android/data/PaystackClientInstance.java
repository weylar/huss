package com.huss.android.data;

import com.huss.android.utility.Utility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaystackClientInstance {

        private static Retrofit retrofit;
        public static Retrofit getPaystackInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(Utility.BASE_URL_PAYSTACK)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

