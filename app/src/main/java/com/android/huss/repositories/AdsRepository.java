package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Ads;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsRepository {

    private static final String TAG = "AdsRepository";
    private static AdsRepository adsRepository;

    public static AdsRepository getInstance(){
        if (adsRepository == null){
            adsRepository = new AdsRepository();
        }
        return adsRepository;
    }

    public MutableLiveData<List<Ads>>getAds() {
        final MutableLiveData<List<Ads>> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<Ads>> call = retrofit.getAds();
        call.enqueue(new Callback<List<Ads>>() {
            @Override
            public void onResponse(Call<List<Ads>> call, Response<List<Ads>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Ads>> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Failed");
              // adsData.setValue(null);
            }
        });
        return adsData;
    }

    public MutableLiveData<Ads>getSingleAds(String id){
        final MutableLiveData<Ads> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Ads> call = retrofit.getSingleAds(id);
        call.enqueue(new Callback<Ads>() {
            @Override
            public void onResponse(Call<Ads> call, Response<Ads> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SINGLE SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Ads> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Single Ads Failed");
                // adsData.setValue(null);
            }
        });
        return adsData;
    }


}





