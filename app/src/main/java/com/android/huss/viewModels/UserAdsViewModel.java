package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ad;
import com.android.huss.models.Ads;
import com.android.huss.models.AllAds;
import com.android.huss.repositories.AdsRepository;

public class UserAdsViewModel extends ViewModel {

    private MutableLiveData<AllAds> mutableLiveDataAllAds;
    private MutableLiveData<Ads> mutableLiveDataDelete;
    private MutableLiveData<AllAds> mutableLiveDataEdit;
    private MutableLiveData<AllAds> mutableLiveDataRenew;
    private AdsRepository adsRepository;

    public void init(String token) {
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataAllAds = adsRepository.getUserAds(token);

    }

    public void initDelete(String token, int id) {
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataDelete = adsRepository.deleteUserAd(token, id);

    }

    public void initEdit(String token) {
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataEdit = adsRepository.getUserAds(token);

    }

    public void initRenew(String token) {
        adsRepository = AdsRepository.getInstance();
        mutableLiveDataEdit = adsRepository.getUserAds(token);

    }


    public LiveData<AllAds> getUserAds() {
        return mutableLiveDataAllAds;
    }

    public LiveData<Ads> deleteUserAd() {
        return mutableLiveDataDelete;
    }

    public LiveData<AllAds> editUserAd() {
        return mutableLiveDataEdit;
    }

    public LiveData<AllAds> renewUserAd() {
        return mutableLiveDataRenew;
    }


}
