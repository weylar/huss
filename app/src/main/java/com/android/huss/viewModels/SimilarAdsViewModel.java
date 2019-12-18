package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.repositories.AdsRepository;

import java.util.List;

public class SimilarAdsViewModel extends ViewModel {


    private MutableLiveData<List<Ads>> mutableLiveDataSingleAds;
    private AdsRepository adsRepository;

    public void init(String name) {
        if (mutableLiveDataSingleAds != null) return;
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataSingleAds = adsRepository.getSimilarAds(name);

    }

    public LiveData<List<Ads>> getSimilarAds() {
        return mutableLiveDataSingleAds;
    }

}
