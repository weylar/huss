package com.huss.android.viewModels.ads;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Ads;
import com.huss.android.repositories.AdsRepository;

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
