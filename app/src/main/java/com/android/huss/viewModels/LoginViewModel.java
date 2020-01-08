package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Profile;
import com.android.huss.repositories.LoginRepository;
import com.android.huss.repositories.ProfileRepository;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Profile> mutableLiveDataProfile;
    private LoginRepository loginRepository;

    public void init(Profile.Data profile) {
        if (mutableLiveDataProfile != null) return;
        loginRepository = LoginRepository.getInstance();
        mutableLiveDataProfile = loginRepository.login(profile);

    }


    public LiveData<Profile> getLoginProfile() {
        return mutableLiveDataProfile;
    }


}
