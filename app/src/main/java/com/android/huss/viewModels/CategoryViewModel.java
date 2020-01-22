package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.Category;
import com.android.huss.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<Category> mutableLiveDatap;
    private MutableLiveData<Category> mutableLiveData;
    private CategoryRepository categoryRepository;

    public void init(String token, int limit) {
        if (mutableLiveDatap != null) {
            return;
        }
        categoryRepository = CategoryRepository.getInstance();
        mutableLiveDatap = categoryRepository.getPopularCategory(token, limit);

    }

    public LiveData<Category> getPopularCategory() {
        return mutableLiveDatap;
    }

    public void initAllCategory(String token) {
        if (mutableLiveData != null) {
            return;
        }
        categoryRepository = CategoryRepository.getInstance();
        mutableLiveData = categoryRepository.getAllCategory(token);

    }

    public LiveData<Category> getAllCategory() {
        return mutableLiveData;
    }

}
