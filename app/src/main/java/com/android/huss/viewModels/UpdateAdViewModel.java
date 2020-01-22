package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.models.SingleAd;
import com.android.huss.repositories.AdsRepository;

public class UpdateAdViewModel extends ViewModel {

    private MutableLiveData<SingleAd> mutableLiveData;

    public void init(String token, SingleAd.Data ad) {
        if (mutableLiveData != null) {
            return;
        }
        AdsRepository adsRepository = AdsRepository.getInstance();
        mutableLiveData = adsRepository.updateAd( token, ad);

    }

    public LiveData<SingleAd> getUpdateAdResponse() {
        return mutableLiveData;
    }

}
