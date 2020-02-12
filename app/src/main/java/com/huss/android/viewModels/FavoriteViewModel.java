package com.huss.android.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Ads;
import com.huss.android.models.AllAds;
import com.huss.android.models.FavoriteAd;
import com.huss.android.repositories.AdsRepository;

import java.util.List;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<FavoriteAd> myFavs;
    private AdsRepository adsRepository = AdsRepository.getInstance();

    public void favoriteAd(String token, String adId) {
        adsRepository.favoriteAds(token, adId);

    }

    public void getMyFavoriteAds(String token) {
        myFavs = adsRepository.getUserFavorite(token);

    }
    public void unFavoriteAd(String token, String adId) {
        adsRepository.unFavoriteAds(token, adId);

    }

    public LiveData<FavoriteAd> getFavAds() {
        return myFavs;
    }

}
