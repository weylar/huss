package com.android.huss.viewModels.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Profile;
import com.android.huss.repositories.auth.ForgotPasswordRepository;

public class ForgotPasswordViewModel extends ViewModel {

    private MutableLiveData<Profile> mutableLiveData;

    public void init(Profile.Data profile) {
        ForgotPasswordRepository loginRepository = ForgotPasswordRepository.getInstance();
        mutableLiveData = loginRepository.forgotPassword(profile);

    }

    public LiveData<Profile> forgotPassword() {
        return mutableLiveData;
    }

}
