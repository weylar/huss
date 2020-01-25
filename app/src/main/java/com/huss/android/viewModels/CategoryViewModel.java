package com.huss.android.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Category;
import com.huss.android.repositories.CategoryRepository;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<Category> mutableLiveDatap;
    private MutableLiveData<Category> mutableLiveData;
    private CategoryRepository categoryRepository;

    public void init(String token) {
        if (mutableLiveDatap != null) {
            return;
        }
        categoryRepository = CategoryRepository.getInstance();
        mutableLiveDatap = categoryRepository.getPopularCategory(token);

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
