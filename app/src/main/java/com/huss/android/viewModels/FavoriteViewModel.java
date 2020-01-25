package com.huss.android.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Ads;
import com.huss.android.repositories.AdsRepository;

import java.util.List;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<List<Ads>> mutableLiveData;
    private AdsRepository adsRepository;

    public void init(String userId) {
        if (mutableLiveData != null) {
            return;
        }
        adsRepository = AdsRepository.getInstance();

        mutableLiveData = adsRepository.getFavoriteAds(userId);

    }

    public LiveData<List<Ads>> getFavoriteAds() {
        return mutableLiveData;
    }

}
