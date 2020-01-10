package com.android.huss.repositories.auth;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Profile;
import com.android.huss.utility.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordRepository {

    private static final String TAG = ResetPasswordRepository.class.getSimpleName();
    private static ResetPasswordRepository loginRepository;

    public static ResetPasswordRepository getInstance(){
        if (loginRepository == null){
            loginRepository = new ResetPasswordRepository();
        }
        return loginRepository;
    }



    public MutableLiveData<Profile> resetPassword(Profile.Data profile) {
        MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.resetPassword(profile.getId(), profile.getToken(), profile.getPassword(), profile.getConfirmPassword());
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Log.e(TAG, "Response");
                if (response.isSuccessful()) {
                    profileData.setValue(response.body());
                }else{
                   profileData.setValue(ErrorUtils.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e(TAG, "Error");
            }
        });
        return profileData;
    }



}





