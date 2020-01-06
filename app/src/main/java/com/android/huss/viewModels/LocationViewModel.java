package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.models.Location;
import com.android.huss.repositories.AdsRepository;
import com.android.huss.repositories.LocationRepository;

import java.util.List;

public class LocationViewModel extends ViewModel {

    private MutableLiveData<List<Location>> mutableLiveDataAllAds;
    private LocationRepository locationRepository;

    public void init() {
        if (mutableLiveDataAllAds != null) return;
        locationRepository = LocationRepository.getInstance();
        mutableLiveDataAllAds = locationRepository.getLocation();

    }


    public LiveData<List<Location>> getLocations() {
        return mutableLiveDataAllAds;
    }


}
