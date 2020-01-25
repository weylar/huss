package com.huss.android.viewModels.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Profile;
import com.huss.android.repositories.auth.LoginRepository;

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
