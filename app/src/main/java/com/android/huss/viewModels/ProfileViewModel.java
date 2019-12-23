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
    private ProfileRepository profileRepository;

    public void init(String userId) {
        if (mutableLiveDataProfile != null) return;
        profileRepository= ProfileRepository.getInstance();
        mutableLiveDataProfile = profileRepository.getUserProfile(userId);

    }


    public LiveData<Profile> getProfile() {
        return mutableLiveDataProfile;
    }


}
