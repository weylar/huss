package com.android.huss.repositories.auth;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Profile;
import com.android.huss.utility.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordRepository {

    private static final String TAG = ForgotPasswordRepository.class.getSimpleName();
    private static ForgotPasswordRepository loginRepository;

    public static ForgotPasswordRepository getInstance(){
        if (loginRepository == null){
            loginRepository = new ForgotPasswordRepository();
        }
        return loginRepository;
    }



    public MutableLiveData<Profile> forgotPassword(Profile.Data profile) {
        MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.forgotPassword(profile.getEmail());
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    profileData.setValue(response.body());
                }else{
                   profileData.setValue(ErrorUtils.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
        return profileData;
    }



}





