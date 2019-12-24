package com.android.huss.views.subCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.huss.R;

import com.android.huss.models.SubCategory;
import com.android.huss.viewModels.SubCategoryViewModel;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class SubCategoryView extends AppCompatActivity {
    RecyclerView subCategoryRecycler;
    SubCategoryViewModel subCategoryViewModel;
    LVCircularZoom progressbar;
    SubCategoryAdapter subCategoryAdapter;
    LinearLayoutManager linearLayoutManager;
    TextView pageTitle;
    String catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        subCategoryRecycler = findViewById(R.id.sub_cat_recycler);
        pageTitle = findViewById(R.id.page_title);
        progressbar = findViewById(R.id.progress);
        progressbar.setViewColor(getResources().getColor(R.color.gray));
        progressbar.startAnim(100);


    }

    @Override
    protected void onStart() {
        super.onStart();
        catName = getIntent().getStringExtra(NAME);
        pageTitle.setText(catName);
        subCategoryViewModel = ViewModelProviders.of(this).get(SubCategoryViewModel.class);
        subCategoryViewModel.init(/*catName*/ "1");
        subCategoryViewModel.getSubCategory().observe(this, ads -> {
            SubCategory subCategory = new SubCategory();
            subCategory.setName("Phones");
            ads.add(subCategory);
            SubCategoryView.this.generateSubcategory(ads);
            progressbar.stopAnim();
            progressbar.setVisibility(View.GONE);
            progressbar.stopAnim();
        });
    }

    private void generateSubcategory(List<SubCategory> ad){
        subCategoryAdapter = new SubCategoryAdapter(this, ad);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        subCategoryRecycler.setLayoutManager(linearLayoutManager);
        subCategoryRecycler.setAdapter(subCategoryAdapter);

    }

    public void goBack(View view){
        finish();
    }
}
