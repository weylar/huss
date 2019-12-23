package com.android.huss.views.latestAds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.AdsViewModel;
import com.android.huss.views.home.LatestAdsAdapter;
import com.android.huss.views.home.MainActivity;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

import static com.android.huss.views.singleAds.SingleAds.NAME;

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
        pageTitle.setText(getIntent().getStringExtra(NAME));
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.init();
        adsViewModel.getAds().observe(this, ads -> {
            LatestAds.this.generateLatestAdsList(ads);
            progressBarLatestAds.stopAnim();
            progressBarLatestAds.setVisibility(View.GONE);
            progressBarLatestAds.stopAnim();
        });

    }


    private void generateLatestAdsList(List<Ads> ads) {
        latestAdsAdapter = new AllLatestAdsAdapter(this, ads);
        layoutManagerAllLatestAds = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        all_latest_ads.setLayoutManager(layoutManagerAllLatestAds);
        all_latest_ads.setAdapter(latestAdsAdapter);


    }
}
