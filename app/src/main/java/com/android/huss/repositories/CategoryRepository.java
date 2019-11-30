package com.android.huss.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Callback;
import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Category;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;


public class CategoryRepository {

    private static final String TAG = "CategoryRepository";
    private static CategoryRepository categoryRepository;

    public static CategoryRepository getInstance(){
        if (categoryRepository == null){
            categoryRepository = new CategoryRepository();
        }
        return categoryRepository;
    }

    public MutableLiveData<List<Category>>getCategories() {
        final MutableLiveData<List<Category>> categoryData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<Category>> call = retrofit.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SUCCESS");
                    categoryData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Failed");
//               categoryData.setValue(null);

            }
        });
        return categoryData;
    }


}





