package com.android.huss.repositories;

import android.util.Log;
import retrofit2.Callback;
import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Category;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;


import static com.android.huss.utility.Utility.ACCESS_KEY;

public class CategoryRepository {

    private static final String TAG = "CategoryRepository";


    public void getCategories() {
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<Category>> call = retrofit.getCategory(ACCESS_KEY);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: SUCCESS");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, "onFailure: ERROR LOADING DATA");
                Log.e(TAG, t.getMessage() + "Failed");
            }
        });

    }


}





