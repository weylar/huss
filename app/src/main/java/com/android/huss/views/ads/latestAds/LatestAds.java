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
import com.android.huss.models.AllAds;
import com.android.huss.viewModels.AdsViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.android.huss.utility.Utility.KEY;
import static com.android.huss.utility.Utility.MY_PREFERENCES;
import static com.android.huss.utility.Utility.TOKEN;
import static com.android.huss.views.ads.singleAds.SingleAds.NAME;
import static com.android.huss.views.subCategory.SubCategoryAdapter.CATEGORY;

public class LatestAds extends AppCompatActivity {
    RecyclerView all_latest_ads;
    RecyclerView.LayoutManager layoutManagerAllLatestAds;
    AllLatestAdsAdapter latestAdsAdapter;
    AdsViewModel adsViewModel;
    ShimmerFrameLayout shimmerFrameLayout;
    TextView pageTitle;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_ads);
        token = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "");
        pageTitle = findViewById(R.id.page_title);
        all_latest_ads = findViewById(R.id.all_latest_ads);
        shimmerFrameLayout = findViewById(R.id.shimmer);

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
            pageTitle.setText(String.format("Search results for %s", key));
            adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
            adsViewModel.initSearchAds(getIntent().getStringExtra(KEY));
            adsViewModel.getSearchAds().observe(this, ads -> {
                generateLatestAdsList(ads.getData());
                shimmerFrameLayout.hideShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

            });
        }else {
            if (getIntent().getStringExtra(CATEGORY) == null){
                pageTitle.setText(getIntent().getStringExtra(NAME));
                adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
                adsViewModel.initAllAds(token);
                adsViewModel.getAllAds().observe(this, ads -> {
                    generateLatestAdsList(ads.getData());
                    shimmerFrameLayout.hideShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                });
            }else {
                pageTitle.setText(String.format("%s >> %s", getIntent().getStringExtra(CATEGORY), getIntent().getStringExtra(NAME)));
                adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
                adsViewModel.initAllAds(token);
                adsViewModel.getAllAds().observe(this, ads -> {
                    List<AllAds.Data> filtered = new ArrayList<>();
                    for (AllAds.Data ad : ads.getData()){
                        if (ad.getCategoryName().equals(getIntent().getStringExtra(CATEGORY))
                                && ad.getSubCategoryName().equals(getIntent().getStringExtra(NAME))){
                            filtered.add(ad);
                        }

                    }
                    generateLatestAdsList(filtered);
                    shimmerFrameLayout.hideShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                });
            }


        }
    }


    private void generateLatestAdsList(List<AllAds.Data> ads) {
        latestAdsAdapter = new AllLatestAdsAdapter(this, ads);
        layoutManagerAllLatestAds = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        all_latest_ads.setLayoutManager(layoutManagerAllLatestAds);
        all_latest_ads.setAdapter(latestAdsAdapter);


    }
}
