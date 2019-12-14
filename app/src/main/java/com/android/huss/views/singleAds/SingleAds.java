package com.android.huss.views.singleAds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.AdsViewModel;
import com.android.huss.viewModels.SingleAdsViewModel;
import com.android.huss.views.latestAds.AllLatestAdsAdapter;
import com.android.huss.views.latestAds.LatestAds;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.AdsViewModel;
import com.android.huss.views.home.LatestAdsAdapter;
import com.android.huss.views.home.MainActivity;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

public class SingleAds extends AppCompatActivity {

    ViewPager viewPager;
    Pager pager;
    SingleAdsViewModel adsViewModel;
    LVCircularZoom progressBarLatestAds;
    String id;
    public static final String ID = "AdsId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ads);
        viewPager = findViewById(R.id.viewPagerAds);
        progressBarLatestAds = findViewById(R.id.progress);
        progressBarLatestAds.setViewColor(getResources().getColor(R.color.colorAccent));
        progressBarLatestAds.startAnim(100);
        id = getIntent().getStringExtra(ID);


    }

    public void goBack(View view) {
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*Get latest ads using view model*/
        adsViewModel = ViewModelProviders.of(this).get(SingleAdsViewModel.class);
        adsViewModel.init(id);
        adsViewModel.getSingleAds().observe(this, new Observer<Ads>() {
            @Override
            public void onChanged(Ads ads) {
                SingleAds.this.generateLatestAdsList(ads);
                progressBarLatestAds.stopAnim();
                progressBarLatestAds.setVisibility(View.GONE);
                progressBarLatestAds.stopAnim();
            }
        });

    }

    private void generateLatestAdsList(Ads ads) {
        pager = new Pager(this, ads.getAllImgUrl());
        viewPager.setAdapter(pager);


    }


}
