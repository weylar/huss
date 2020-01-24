package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.models.AllAds;
import com.android.huss.models.SingleAd;
import com.android.huss.repositories.AdsRepository;

import java.util.List;

public class AdsViewModel extends ViewModel {

    private MutableLiveData<AllAds> mutableLiveDataAllAdsByLimit;
    private MutableLiveData<AllAds> mutableLiveDataAllAds;
    private MutableLiveData<SingleAd> mutableLiveDataSingle;
    private AdsRepository adsRepository;

    public void initAllAdsByLimit(String token) {
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataAllAdsByLimit = adsRepository.getAllAdsByLimit(token );

    }


    public LiveData<AllAds> getAllAdsByLimit() {
        return mutableLiveDataAllAdsByLimit;
    }

    public void initAllAds(String token) {
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataAllAds = adsRepository.getAllAds(token );

    }


    public LiveData<AllAds> getAllAds() {
        return mutableLiveDataAllAds;
    }

    public void initSearchAds(String key) {
        if (mutableLiveDataAllAds != null) return;
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataAllAds = adsRepository.getSearchAds(key);

    }

    public void initSingleAd(String key, String token) {
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataSingle = adsRepository.getSingleAds(key, token);

    }

    public LiveData<SingleAd> getSingleAd() {
        return mutableLiveDataSingle;
    }

    public LiveData<AllAds> getSearchAds() {
        return mutableLiveDataAllAds;
    }


}
