package com.huss.android.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.huss.android.data.PaystackAPI;
import com.huss.android.data.PaystackClientInstance;
import com.huss.android.models.Banks;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentRepository {

    private static final String TAG = "PaymentRepository";
    private static PaymentRepository categoryRepository;

    public static PaymentRepository getInstance(){
        if (categoryRepository == null){
            categoryRepository = new PaymentRepository();
        }
        return categoryRepository;
    }

    public MutableLiveData<List<Banks.Datum>>getBanks(String gateway, boolean isPayWithBank) {
        final MutableLiveData<List<Banks.Datum>> banks = new MutableLiveData<>();
        PaystackAPI retrofit = PaystackClientInstance.getPaystackInstance().create(PaystackAPI.class);
        Call<Banks> call = retrofit.getBanks(gateway, isPayWithBank);
        call.enqueue(new Callback<Banks>() {
            @Override
            public void onResponse(Call<Banks> call, Response<Banks> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SUCCESS");
                    Banks resource = response.body();
                    List<Banks.Datum> datumList = resource.data;
                    banks.setValue(datumList);
                }
            }

            @Override
            public void onFailure(Call<Banks> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Failed");

            }
        });
        return banks;
    }


}




