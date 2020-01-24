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

import static com.android.huss.utility.Utility.BEARER;


public class CategoryRepository {

    private static final String TAG = "CategoryRepository";
    private static CategoryRepository categoryRepository;

    public static CategoryRepository getInstance(){
        if (categoryRepository == null){
            categoryRepository = new CategoryRepository();
        }
        return categoryRepository;
    }

    public MutableLiveData<Category>getPopularCategory(String token) {
        final MutableLiveData<Category> categoryData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Category> call = retrofit.getPopularCategory(BEARER + " " + token);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call <Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SUCCESS");
                    categoryData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Failed");

            }
        });
        return categoryData;
    }

    public MutableLiveData<Category> getAllCategory(String token) {
        final MutableLiveData<Category> categoryData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Category> call = retrofit.getAllCategory("Bearer " + token);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SUCCESS");
                    categoryData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.e(TAG, t.getMessage() + "Failed");
//               categoryData.setValue(null);

            }
        });
        return categoryData;
    }


}





