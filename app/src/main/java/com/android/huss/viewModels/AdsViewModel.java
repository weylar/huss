package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.models.Category;
import com.android.huss.repositories.AdsRepository;
import com.android.huss.repositories.CategoryRepository;

import java.util.List;

public class AdsViewModel extends ViewModel {

    private MutableLiveData<List<Ads>> mutableLiveDataAllAds;
    private AdsRepository adsRepository;

    public void init() {
        if (mutableLiveDataAllAds != null) return;
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataAllAds = adsRepository.getAds();

    }


    public LiveData<List<Ads>> getAds() {
        return mutableLiveDataAllAds;
    }


}
