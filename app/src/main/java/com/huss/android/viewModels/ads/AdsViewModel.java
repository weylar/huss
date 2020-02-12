package com.huss.android.viewModels.ads;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.AllAds;
import com.huss.android.models.SingleAd;
import com.huss.android.repositories.AdsRepository;

public class AdsViewModel extends ViewModel {

    private MutableLiveData<AllAds> mutableLiveDataAllAdsByLimit;
    private MutableLiveData<AllAds> mutableLiveDataAllAds;
    private MutableLiveData<SingleAd> mutableLiveDataSingle;
    private AdsRepository adsRepository = AdsRepository.getInstance();

    public void initAllAdsByLimit() {

        mutableLiveDataAllAdsByLimit = adsRepository.getAllAdsByLimit();

    }


    public LiveData<AllAds> getAllAdsByLimit() {
        return mutableLiveDataAllAdsByLimit;
    }

    public void initAllAds() {
        mutableLiveDataAllAds = adsRepository.getAllAds();

    }


    public LiveData<AllAds> getAllAds() {
        return mutableLiveDataAllAds;
    }



    public LiveData<SingleAd> getSingleAd(String token, String key) {
        mutableLiveDataSingle = adsRepository.getSingleAds(token, key);
        return mutableLiveDataSingle;
    }



}
