package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Ads;
import com.android.huss.models.Category;
import com.android.huss.repositories.CategoryRepository;
import com.android.huss.repositories.CreateAdRepository;

import java.util.List;

public class CreateAdViewModel extends ViewModel {

    private MutableLiveData<Integer> mutableLiveData;

    public void init(Ads ads, String category, String subcat, String token) {
        if (mutableLiveData != null) {
            return;
        }
        CreateAdRepository createAdRepository = CreateAdRepository.getInstance();
        mutableLiveData = createAdRepository.createAd(ads, category, subcat, token);

    }

    public LiveData<Integer> getCreateResponse() {
        return mutableLiveData;
    }

}
