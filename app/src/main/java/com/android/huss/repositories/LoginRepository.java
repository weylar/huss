package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();
    private static LoginRepository loginRepository;

    public static LoginRepository getInstance(){
        if (loginRepository == null){
            loginRepository = new LoginRepository();
        }
        return loginRepository;
    }



    public MutableLiveData<Profile> login(Profile.Data profile) {
        final MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.login(profile);
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





