package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Category;
import com.android.huss.models.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationRepository {

    private static final String TAG = "LocationRepository";
    private static LocationRepository locationRepository;

    public static LocationRepository getInstance(){
        if (locationRepository == null){
            locationRepository = new LocationRepository();
        }
        return locationRepository;
    }

    public MutableLiveData<List<Location>>getLocation() {
        final MutableLiveData<List<Location>> locationData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<Location>> call = retrofit.getLocation();
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SUCCESS");
                    locationData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Failed");
//               categoryData.setValue(null);

            }
        });
        return locationData;
    }


}





