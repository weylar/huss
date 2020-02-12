package com.huss.android.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.huss.android.data.HussAPI;
import com.huss.android.data.RetrofitClientInstance;
import com.huss.android.models.Image;
import com.huss.android.utility.Utility;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateImageRepository {

    private static final String TAG = "CreateImageRepository";
    private static CreateImageRepository categoryRepository;

    public static CreateImageRepository getInstance() {
        if (categoryRepository == null) {
            categoryRepository = new CreateImageRepository();
        }
        return categoryRepository;
    }



    public MutableLiveData<Image> createImage(Image.Data adImage, String token) {
        final MutableLiveData<Image> adData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Image> call = retrofit.createImage(adImage.getAdId(), adImage.getIconUrl(),
                adImage.getFeatured(), Utility.BEARER + " " + token);
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(@NotNull Call<Image> call, @NotNull Response<Image> response) {
                if (response.isSuccessful()) {
                    Log.e("Aminu", response.message());
                    adData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Image> call, @NotNull Throwable throwable) {
                Log.e("Aminu", throwable.getMessage());
            }
        });
        return adData;
    }

// public MutableLiveData<AdImage> getFeaturedImage(Ad.Data adImage, String token) {
//        final MutableLiveData<AdImage> adData = new MutableLiveData<>();
//        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
//        Call<AdImage> call = retrofit.getFeaturedImage(adImage.getId(), Utility.BEARER + " " + token);
//        call.enqueue(new Callback<AdImage>() {
//            @Override
//            public void onResponse(@NotNull Call<AdImage> call, @NotNull Response<AdImage> response) {
//                if (response.isSuccessful()) {
//                    adData.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<AdImage> call, @NotNull Throwable throwable) {
//                Log.e("Aminu", throwable.getMessage());
//            }
//        });
//        return adData;
//    }


}





