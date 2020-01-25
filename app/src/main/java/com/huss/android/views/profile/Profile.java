package com.huss.android.views.profile;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.huss.android.R;
import com.huss.android.models.AllAds;
import com.huss.android.viewModels.UserAdsViewModel;
import com.huss.android.views.settings.SettingsActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.huss.android.utility.Utility;
import com.huss.android.views.ads.singleAds.SingleAds;
import com.huss.android.views.home.MainActivity;
import com.huss.android.views.settings.SettingsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    ViewPager vpPager;
    TabLayout tabLayout;
    ProfileAdsPager profileAdsPager;
    TextView totalAdsCount, totalActiveAdsCount, totalInActiveAdsCount, name, phone, email;
    CircleImageView profileImage;
    TextView placeholder;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        vpPager = findViewById(R.id.viewPagerProfile);
        tabLayout = findViewById(R.id.tabLayoutProfile);
        totalActiveAdsCount = findViewById(R.id.activeAdsCount);
        totalAdsCount = findViewById(R.id.totalAdsCount);
        totalInActiveAdsCount = findViewById(R.id.inActiveAdsCount);
        profileImage = findViewById(R.id.profile_img);
        name = findViewById(R.id.profileName);
        placeholder = findViewById(R.id.placeholder);
        phone = findViewById(R.id.phone);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        email = findViewById(R.id.email);
        root = findViewById(R.id.root);

//        Blurry.with(this)
//                .radius(10)
//                .sampling(8)
//                .color(Color.argb(66, 255, 255, 0))
//                .async()
//                .animate(500)
//                .onto(root);



    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(MainActivity.checkNetworkConnection(this, shimmerFrameLayout), filter);

        name.setText(getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.USER_NAME, ""));
        email.setText(getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.EMAIL, ""));
        String phoneN = getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.PHONE, "");
        if (phoneN.isEmpty()) {
            phone.setVisibility(View.GONE);
        } else {
            phone.setVisibility(View.VISIBLE);
            phone.setText(phoneN);
        }

        loadProfileImage(profileImage, placeholder, getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.PROFILE_IMAGE_URL,
                Utility.DEFAULT_IMAGE));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAdsDetails();

    }

    private void loadProfileImage(View imageview, TextView placeholder, String url) {
        if (url.equals(Utility.DEFAULT_IMAGE)) {
            placeholder.setVisibility(View.VISIBLE);
            imageview.setVisibility(View.GONE);
            placeholder.setText(getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.USER_NAME, "").charAt(0) + "");
        } else {
            imageview.setVisibility(View.VISIBLE);
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.build().load(url)
                    .into((ImageView) imageview);
        }
    }

    private void getAdsDetails() {
        UserAdsViewModel userAdsViewModel = ViewModelProviders.of(this).get(UserAdsViewModel.class);
        userAdsViewModel.init(getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(Utility.TOKEN, ""));
        userAdsViewModel.getUserAds().observe(this, ads -> {
            int activeCount = 0;
            int inactiveCount = 0;
            for (AllAds.Data ad : ads.getData()) {
                if (ad.getStatus().equals(getString(R.string.active))) activeCount++;
                else inactiveCount++;
            }
            totalAdsCount.setText(SingleAds.formatViews(ads.getData().size() + ""));
            totalActiveAdsCount.setText(SingleAds.formatViews(activeCount + ""));
            totalInActiveAdsCount.setText(SingleAds.formatViews(inactiveCount + ""));
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.hideShimmer();
            List<AllAds.Data> active = new ArrayList<>();
            List<AllAds.Data> inactive = new ArrayList<>();
            for (AllAds.Data ad: ads.getData()){
                if (ad.getStatus().equals(getString(R.string.active))){
                    active.add(ad);
                }else{
                    inactive.add(ad);
                }
            }
            profileAdsPager = new ProfileAdsPager(getSupportFragmentManager(), active, inactive);
            vpPager.setAdapter(profileAdsPager);
            tabLayout.setupWithViewPager(vpPager);

        });


    }


    public void goBack(View view) {
        finish();
    }

    public void openSetting(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }


}
