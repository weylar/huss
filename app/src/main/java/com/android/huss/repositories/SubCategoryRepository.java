package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Category;
import com.android.huss.models.SubCategory;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubCategoryRepository {

    private static final String TAG = "SubCategoryRepository";
    private static SubCategoryRepository subCategoryRepository;

    public static SubCategoryRepository getInstance(){
        if (subCategoryRepository == null){
            subCategoryRepository = new SubCategoryRepository();
        }
        return subCategoryRepository;
    }

    public MutableLiveData<List<SubCategory>>getSubCategories(String catName) {
        final MutableLiveData<List<SubCategory>> subCategoryRepository = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<SubCategory>> call = retrofit.getSubCategory(catName);
        call.enqueue(new Callback<List<SubCategory>>() {
            @Override
            public void onResponse(Call<List<SubCategory>> call, Response<List<SubCategory>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SUCCESS");
                    subCategoryRepository.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SubCategory>> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Failed");
            }
        });
        return subCategoryRepository;
    }


}





