package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.models.Profile;
import com.android.huss.repositories.AdsRepository;
import com.android.huss.repositories.ProfileRepository;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Profile> mutableLiveDataProfile;
    private MutableLiveData<Profile> mutableLiveDataUpdateProfile;
    private ProfileRepository profileRepository;

    public void init(String userId) {
        if (mutableLiveDataProfile != null) return;
        profileRepository= ProfileRepository.getInstance();
        mutableLiveDataProfile = profileRepository.getUserProfile(userId);

    }


    public LiveData<Profile> getProfile() {
        return mutableLiveDataProfile;
    }

    public void initUpdateProfile(Profile.Data profile) {
        profileRepository = ProfileRepository.getInstance();
        mutableLiveDataUpdateProfile = profileRepository.updateProfile(profile);

    }


    public LiveData<Profile> updateProfile() {
        return mutableLiveDataUpdateProfile;
    }

}
