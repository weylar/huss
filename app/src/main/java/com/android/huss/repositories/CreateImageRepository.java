package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Ad;
import com.android.huss.models.AdImage;
import com.android.huss.models.Image;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.huss.utility.Utility.BEARER;


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
                adImage.getFeatured(), BEARER + " " + token);
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

 public MutableLiveData<AdImage> getFeaturedImage(Ad.Data adImage, String token) {
        final MutableLiveData<AdImage> adData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<AdImage> call = retrofit.getFeaturedImage(adImage.getId(), BEARER + " " + token);
        call.enqueue(new Callback<AdImage>() {
            @Override
            public void onResponse(@NotNull Call<AdImage> call, @NotNull Response<AdImage> response) {
                if (response.isSuccessful()) {
                    adData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AdImage> call, @NotNull Throwable throwable) {
                Log.e("Aminu", throwable.getMessage());
            }
        });
        return adData;
    }


}





