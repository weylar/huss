package com.android.huss.views.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.models.Category;
import com.android.huss.viewModels.AdsViewModel;
import com.android.huss.viewModels.CategoryViewModel;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;
import com.ldoublem.loadingviewlib.view.LVGearsTwo;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView catRecyclerView;
    RecyclerView topAdsRecyclerView;
    CategoryAdapter categoryAdapter;
    TopAdsAdapter topAdsAdapter;
    RecyclerView.LayoutManager layoutManagerCat, layoutManagerTop;
    CategoryViewModel categoryViewModel;
    AdsViewModel adsViewModel;
    LVCircularZoom progressBar;
    LVCircularZoom progressBarTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress);
        progressBarTop = findViewById(R.id.progressTop);
        progressBar.setViewColor(getResources().getColor(R.color.gray));
        progressBarTop.setViewColor(getResources().getColor(R.color.colorAccent));
        progressBar.startAnim(100);
        progressBarTop.startAnim(100);


    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Get all categories using view model*/
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.init();
        categoryViewModel.getCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> catResponse) {
                MainActivity.this.generateCategoryList(catResponse);
                progressBar.stopAnim();
                progressBar.setVisibility(View.GONE);
            }
        });
        /*Get top ads using view model*/
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.init();
        adsViewModel.getAds().observe(this, new Observer<List<Ads>>() {
            @Override
            public void onChanged(List<Ads> ads) {
                MainActivity.this.generateTopAdsList(ads);
                progressBarTop.stopAnim();
                progressBarTop.setVisibility(View.GONE);
            }
        });

    }

    private void generateCategoryList(List<Category> categories) {
        catRecyclerView = findViewById(R.id.recycler_cat);
        categoryAdapter = new CategoryAdapter(this, categories);
        layoutManagerCat = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        catRecyclerView.setLayoutManager(layoutManagerCat);
        catRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }

    private void generateTopAdsList(List<Ads> ads){
        topAdsRecyclerView = findViewById(R.id.recycler_top_ads);
        topAdsAdapter = new TopAdsAdapter(this, ads);
        layoutManagerTop = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topAdsRecyclerView.setLayoutManager(layoutManagerTop);
        topAdsRecyclerView.setAdapter(topAdsAdapter);

    }
}
