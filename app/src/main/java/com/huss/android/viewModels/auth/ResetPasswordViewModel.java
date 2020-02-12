package com.huss.android.viewModels.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Profile;
import com.huss.android.repositories.auth.ResetPasswordRepository;

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
