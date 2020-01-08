package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.AdImage;
import com.android.huss.repositories.CreateAdRepository;

public class CreateImageViewModel extends ViewModel {

    private MutableLiveData<String> mutableLiveData;

    public void init(AdImage adImage) {
        if (mutableLiveData != null) {
            return;
        }
        CreateAdRepository createAdRepository = CreateAdRepository.getInstance();
        mutableLiveData = createAdRepository.createImage(adImage);

    }

    public LiveData<String> getCreateImageResponse() {
        return mutableLiveData;
    }

}
