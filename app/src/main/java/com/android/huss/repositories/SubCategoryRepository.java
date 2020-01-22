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

import static com.android.huss.utility.Utility.BEARER;


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
        Call<SubCategory> call = retrofit.getSubCategory(BEARER + " " + token, catName);
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





