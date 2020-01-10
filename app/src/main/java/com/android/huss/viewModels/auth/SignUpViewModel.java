package com.android.huss.viewModels.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Profile;
import com.android.huss.repositories.auth.SignUpRepository;

public class SignUpViewModel extends ViewModel {

    private MutableLiveData<Profile> mutableLiveData;

    public void init(Profile.Data profile) {
        SignUpRepository signupRepository = SignUpRepository.getInstance();
        mutableLiveData = signupRepository.signup(profile);

    }

    public LiveData<Profile> signup() {
        return mutableLiveData;
    }

}
