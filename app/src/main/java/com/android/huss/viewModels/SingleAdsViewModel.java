package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.repositories.AdsRepository;

import java.util.List;

public class SingleAdsViewModel extends ViewModel {


    private MutableLiveData<Ads> mutableLiveDataSingleAds;
    private AdsRepository adsRepository;

    public void init(String id) {
        if (mutableLiveDataSingleAds != null) return;
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataSingleAds = adsRepository.getSingleAds(id);

    }

    public LiveData<Ads> getSingleAds() {
        return mutableLiveDataSingleAds;
    }

}
