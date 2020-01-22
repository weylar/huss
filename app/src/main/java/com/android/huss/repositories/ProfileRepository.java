package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Ads;
import com.android.huss.models.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



    public MutableLiveData<Profile> getUserProfile(String id) {
        final MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.getUserProfile(id);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponseUserProfile: SUCCESS");
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "FailedUserProfile");
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
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponseUserProfile: SUCCESS");
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "FailedUserProfile");
            }
        });
        return profileData;
    }



}





