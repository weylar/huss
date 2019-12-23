package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.SubCategory;
import com.android.huss.repositories.SubCategoryRepository;

import java.util.List;

public class SubCategoryViewModel extends ViewModel {

    private MutableLiveData<List<SubCategory>> mutableLiveData;
    private SubCategoryRepository subCategoryRepository;

    public void init(String catName) {
        if (mutableLiveData != null) {
            return;
        }
        subCategoryRepository = SubCategoryRepository.getInstance();
        mutableLiveData = subCategoryRepository.getSubCategories(catName);

    }

    public LiveData<List<SubCategory>> getSubCategory() {
        return mutableLiveData;
    }

}
