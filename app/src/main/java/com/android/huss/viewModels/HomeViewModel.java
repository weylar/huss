package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Category;
import com.android.huss.repositories.CategoryRepository;

import java.util.List;

import static com.android.huss.utility.Utility.ACCESS_KEY;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Category>> mutableLiveData;
    private CategoryRepository categoryRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        categoryRepository = CategoryRepository.getInstance();
        mutableLiveData = categoryRepository.getCategories();

    }

    public LiveData<List<Category>> getCategory() {
        return mutableLiveData;
    }

}
