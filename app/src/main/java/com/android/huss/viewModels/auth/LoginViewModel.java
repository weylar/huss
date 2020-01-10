package com.android.huss.viewModels.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Profile;
import com.android.huss.repositories.auth.LoginRepository;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Profile> mutableLiveData;

    public void init(Profile.Data profile) {
        LoginRepository loginRepository = LoginRepository.getInstance();
        mutableLiveData = loginRepository.login(profile);

    }

    public LiveData<Profile> login() {
        return mutableLiveData;
    }

}
