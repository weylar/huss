package com.huss.android.viewModels.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Profile;
import com.huss.android.repositories.auth.SignUpRepository;

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
