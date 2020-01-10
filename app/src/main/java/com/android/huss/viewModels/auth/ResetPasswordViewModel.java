package com.android.huss.viewModels.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Profile;
import com.android.huss.repositories.auth.ResetPasswordRepository;

public class ResetPasswordViewModel extends ViewModel {

    private MutableLiveData<Profile> mutableLiveData;

    public void init(Profile.Data profile) {
        ResetPasswordRepository loginRepository = ResetPasswordRepository.getInstance();
        mutableLiveData = loginRepository.resetPassword(profile);

    }

    public LiveData<Profile> resetPassword() {
        return mutableLiveData;
    }

}
