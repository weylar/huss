package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.huss.models.Ads;
import com.android.huss.repositories.AdsRepository;

public class CreateAdViewModel extends ViewModel {

    private MutableLiveData<Ads> mutableLiveData;

    public void init(Ads.Data ads, String token) {
        if (mutableLiveData != null) {
            return;
        }
        AdsRepository adsRepository = AdsRepository.getInstance();
        mutableLiveData = adsRepository.createAd(ads, token);

    }

    public LiveData<Ads> getCreateResponse() {
        return mutableLiveData;
    }

}
