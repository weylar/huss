package com.huss.android.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Location;
import com.huss.android.repositories.LocationRepository;

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
