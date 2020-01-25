package com.huss.android.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Profile;
import com.huss.android.repositories.ProfileRepository;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Profile> mutableLiveDataProfile;
    private MutableLiveData<Profile> mutableLiveDataUpdateProfile;
    private MutableLiveData<Profile> mutableLiveDataUpdateLastSeen;
    private ProfileRepository profileRepository;

    public void init(String token, String userId) {
        if (mutableLiveDataProfile != null) return;
        profileRepository= ProfileRepository.getInstance();
        mutableLiveDataProfile = profileRepository.getUserProfile(token, userId);

    }
    public void initLastSeen(String token, boolean isOnline) {
        profileRepository= ProfileRepository.getInstance();
        mutableLiveDataUpdateLastSeen = profileRepository.updateLastSeen(token, isOnline);

    }

    public LiveData<Profile> updateLastSeen() {
        return mutableLiveDataUpdateLastSeen;
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
