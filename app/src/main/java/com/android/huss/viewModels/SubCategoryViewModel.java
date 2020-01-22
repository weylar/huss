package com.android.huss.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.huss.models.SubCategory;
import com.android.huss.repositories.SubCategoryRepository;

import java.util.List;

public class SubCategoryViewModel extends ViewModel {

    private MutableLiveData<SubCategory> mutableLiveData;
    private SubCategoryRepository subCategoryRepository;

    public void init(String token, String catName) {
        subCategoryRepository = SubCategoryRepository.getInstance();
        mutableLiveData = subCategoryRepository.getSubCategories(token, catName);

    }

    public LiveData<SubCategory> getSubCategory() {
        return mutableLiveData;
    }

}
