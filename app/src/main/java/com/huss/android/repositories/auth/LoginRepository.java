package com.huss.android.repositories.auth;

import androidx.lifecycle.MutableLiveData;

import com.huss.android.data.HussAPI;
import com.huss.android.data.RetrofitClientInstance;
import com.huss.android.models.Profile;
import com.huss.android.utility.ErrorUtils;

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
        MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.login(profile.getEmail(), profile.getPassword());
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





