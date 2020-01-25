package com.huss.android.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.huss.android.models.Ads;
import com.huss.android.repositories.AdsRepository;

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
