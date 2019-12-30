package com.android.huss.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.huss.utility.Utility.BASE_URL;
import static com.android.huss.utility.Utility.BASE_URL_PAYSTACK;

public class PaystackClientInstance {

        private static Retrofit retrofit;
        public static Retrofit getPaystackInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL_PAYSTACK)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

