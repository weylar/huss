package com.huss.android.viewModels.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Profile;
import com.huss.android.repositories.auth.ForgotPasswordRepository;

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
