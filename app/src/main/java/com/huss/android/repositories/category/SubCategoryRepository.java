package com.huss.android.repositories.category;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.huss.android.data.HussAPI;
import com.huss.android.data.RetrofitClientInstance;
import com.huss.android.models.SubCategory;
import com.huss.android.utility.Utility;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubCategoryRepository {

    private static final String TAG = SubCategoryRepository.class.getSimpleName();
    private static SubCategoryRepository subCategoryRepository;

    public static SubCategoryRepository getInstance(){
        if (subCategoryRepository == null){
            subCategoryRepository = new SubCategoryRepository();
        }
        return subCategoryRepository;
    }

    public MutableLiveData<SubCategory> getSubCategories(String token, String catName) {
        final MutableLiveData<SubCategory> subCategoryRepository = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<SubCategory> call = retrofit.getSubCategory(Utility.BEARER + " " + token, catName);
        call.enqueue(new Callback<SubCategory>() {
            @Override
            public void onResponse(Call<SubCategory> call, Response<SubCategory> response) {
                if (response.isSuccessful()) {
                    subCategoryRepository.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SubCategory> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Failed");
            }
        });
        return subCategoryRepository;
    }


}





