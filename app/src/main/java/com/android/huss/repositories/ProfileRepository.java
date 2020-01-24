package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Ads;
import com.android.huss.models.Profile;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.android.huss.utility.Utility.BEARER;

public class ProfileRepository {

    private static final String TAG = "ProfileRepository";
    private static ProfileRepository profileRepository;

    public static ProfileRepository getInstance(){
        if (profileRepository == null){
            profileRepository = new ProfileRepository();
        }
        return profileRepository;
    }



    public MutableLiveData<Profile> getUserProfile(String token, String id) {
        final MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.getUserProfile(BEARER + " " + token,  id);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NotNull Call<Profile> call, @NotNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponseUserProfile: SUCCESS");
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Profile> call, @NotNull Throwable t) {
                Timber.e("%sFailedUserProfile", t.getMessage());
            }
        });
        return profileData;
    }

    public MutableLiveData<Profile> updateProfile(Profile.Data profile ) {
        final MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.updateUserProfile(BEARER + " " + profile.getToken(),
                profile.getProfileImgUrl(),
                profile.getFirstName(), profile.getLastName(), profile.getPhoneNumber(),
                profile.getCity());
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NotNull Call<Profile> call, @NotNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponseUserProfile: SUCCESS");
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Profile> call, @NotNull Throwable t) {
                Timber.e("%sFailedUserProfile", t.getMessage());
            }
        });
        return profileData;
    }



}





