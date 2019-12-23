package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.repositories.AdsRepository;

import java.util.List;

public class UserAdsViewModel extends ViewModel {

    private MutableLiveData<List<Ads>> mutableLiveDataAllAds;
    private AdsRepository adsRepository;

    public void init(String id) {
        if (mutableLiveDataAllAds != null) return;
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataAllAds = adsRepository.getUserAds(id);

    }


    public LiveData<List<Ads>> getUserAds() {
        return mutableLiveDataAllAds;
    }


}
