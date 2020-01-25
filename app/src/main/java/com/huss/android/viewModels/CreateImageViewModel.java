package com.huss.android.viewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Ad;
import com.huss.android.models.AdImage;
import com.huss.android.models.Image;
import com.huss.android.repositories.CreateImageRepository;

public class CreateImageViewModel extends ViewModel {

    private MutableLiveData<Image> mutableLiveData;
    private MutableLiveData<AdImage> mutableLive;

    public void init(Image.Data adImage, String token) {
        CreateImageRepository createAdRepository = CreateImageRepository.getInstance();
        mutableLiveData = createAdRepository.createImage(adImage, token);

    }

    public LiveData<Image> getCreateImageResponse() {
        return mutableLiveData;
    }

    public LiveData<AdImage> getFeaturedImageResponse(Ad.Data adId, String token){
        CreateImageRepository createAdRepository = CreateImageRepository.getInstance();
       return mutableLive = createAdRepository.getFeaturedImage(adId, token);
    }

}
