package com.android.huss.views.ads.latestAds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.AdsViewModel;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.android.huss.utility.Utility.KEY;
import static com.android.huss.views.ads.singleAds.SingleAds.NAME;
import static com.android.huss.views.subCategory.SubCategoryAdapter.CATEGORY;

public class LatestAds extends AppCompatActivity {
    RecyclerView all_latest_ads;
    RecyclerView.LayoutManager layoutManagerAllLatestAds;
    AllLatestAdsAdapter latestAdsAdapter;
    AdsViewModel adsViewModel;
    LVCircularZoom progressBarLatestAds;
    TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_ads);
        pageTitle = findViewById(R.id.page_title);
        all_latest_ads = findViewById(R.id.all_latest_ads);
        progressBarLatestAds = findViewById(R.id.progress);
        progressBarLatestAds.setViewColor(getResources().getColor(R.color.gray));
        progressBarLatestAds.startAnim(100);


    }

    public void goBack(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Get latest ads using view model*/
        if (Objects.requireNonNull(getIntent().hasExtra(KEY))){
            String key =  Objects.requireNonNull(getIntent().getStringExtra(KEY));
            pageTitle.setText("Search results for " + key);
            adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
            adsViewModel.initSearchAds(getIntent().getStringExtra(KEY));
            adsViewModel.getSearchAds().observe(this, ads -> {
                LatestAds.this.generateLatestAdsList(ads);
                progressBarLatestAds.stopAnim();
                progressBarLatestAds.setVisibility(View.GONE);
                progressBarLatestAds.stopAnim();
            });
        }else {
            if (getIntent().getStringExtra(CATEGORY) == null){
                pageTitle.setText(getIntent().getStringExtra(NAME));
            }else {
                pageTitle.setText(getIntent().getStringExtra(CATEGORY) + " >> " + getIntent().getStringExtra(NAME));
            }
            adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
            adsViewModel.init();
            adsViewModel.getAds().observe(this, ads -> {
                LatestAds.this.generateLatestAdsList(ads);
                progressBarLatestAds.stopAnim();
                progressBarLatestAds.setVisibility(View.GONE);
                progressBarLatestAds.stopAnim();
            });
            LatestAds.this.generateLatestAdsList(new ArrayList<>());
        }
    }


    private void generateLatestAdsList(List<Ads> ads) {
        latestAdsAdapter = new AllLatestAdsAdapter(this, ads);
        layoutManagerAllLatestAds = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        all_latest_ads.setLayoutManager(layoutManagerAllLatestAds);
        all_latest_ads.setAdapter(latestAdsAdapter);


    }
}
