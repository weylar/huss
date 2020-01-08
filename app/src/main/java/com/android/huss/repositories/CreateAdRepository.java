package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.AdImage;
import com.android.huss.models.Ads;
import com.android.huss.models.Category;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateAdRepository {

    private static final String TAG = "CreateAdRepository";
    private static CreateAdRepository categoryRepository;

    public static CreateAdRepository getInstance() {
        if (categoryRepository == null) {
            categoryRepository = new CreateAdRepository();
        }
        return categoryRepository;
    }

    public MutableLiveData<Integer> createAd(Ads ads, String category, String subcat, String token) {
        final MutableLiveData<Integer> adData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Integer> call = retrofit.createAd(ads, category, subcat, token);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                if (response.isSuccessful()) {
                    adData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Integer> call, @NotNull Throwable throwable) {

            }
        });
        return adData;
    }

    public MutableLiveData<String> createImage(AdImage adImage) {
        final MutableLiveData<String> adData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<String> call = retrofit.createImage(adImage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.isSuccessful()) {
                    adData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable throwable) {

            }
        });
        return adData;
    }


}





