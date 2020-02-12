package com.huss.android.viewModels.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.SubCategory;
import com.huss.android.repositories.category.SubCategoryRepository;

public class SubCategoryViewModel extends ViewModel {

    private MutableLiveData<SubCategory> mutableLiveData;

    public void init(String token, String catName) {
        SubCategoryRepository subCategoryRepository = SubCategoryRepository.getInstance();
        mutableLiveData = subCategoryRepository.getSubCategories(token, catName);

    }

    public LiveData<SubCategory> getSubCategory() {
        return mutableLiveData;
    }

}
