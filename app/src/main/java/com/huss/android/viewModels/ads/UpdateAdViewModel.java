package com.huss.android.viewModels.ads;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.SingleAd;
import com.huss.android.repositories.AdsRepository;

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
