package com.android.huss.views.profile;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.android.huss.R;
import com.android.huss.models.AllAds;
import com.android.huss.viewModels.UserAdsViewModel;
import com.android.huss.views.settings.SettingsActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.blurry.Blurry;

import static com.android.huss.utility.Utility.DEFAULT_IMAGE;
import static com.android.huss.utility.Utility.EMAIL;
import static com.android.huss.utility.Utility.MY_PREFERENCES;
import static com.android.huss.utility.Utility.PHONE;
import static com.android.huss.utility.Utility.PROFILE_IMAGE_URL;
import static com.android.huss.utility.Utility.TOKEN;
import static com.android.huss.utility.Utility.USER_NAME;
import static com.android.huss.views.ads.singleAds.SingleAds.formatViews;
import static com.android.huss.views.home.MainActivity.checkNetworkConnection;

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

        Blurry.with(this)
                .radius(10)
                .sampling(8)
                .color(Color.argb(66, 255, 255, 0))
                .async()
                .animate(500)
                .onto(root);



    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkNetworkConnection(this, shimmerFrameLayout), filter);

        name.setText(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(USER_NAME, ""));
        email.setText(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(EMAIL, ""));
        String phoneN = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(PHONE, "");
        if (phoneN.isEmpty()) {
            phone.setVisibility(View.GONE);
        } else {
            phone.setVisibility(View.VISIBLE);
            phone.setText(phoneN);
        }

        loadProfileImage(profileImage, placeholder, getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(PROFILE_IMAGE_URL,
                DEFAULT_IMAGE));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAdsDetails();

    }

    private void loadProfileImage(View imageview, TextView placeholder, String url) {
        if (url.equals(DEFAULT_IMAGE)) {
            placeholder.setVisibility(View.VISIBLE);
            imageview.setVisibility(View.GONE);
            placeholder.setText(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(USER_NAME, "").charAt(0) + "");
        } else {
            imageview.setVisibility(View.VISIBLE);
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.build().load(url)
                    .into((ImageView) imageview);
        }
    }

    private void getAdsDetails() {
        UserAdsViewModel userAdsViewModel = ViewModelProviders.of(this).get(UserAdsViewModel.class);
        userAdsViewModel.init(getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE).getString(TOKEN, ""));
        userAdsViewModel.getUserAds().observe(this, ads -> {
            int activeCount = 0;
            int inactiveCount = 0;
            for (AllAds.Data ad : ads.getData()) {
                if (ad.getStatus().equals(getString(R.string.active))) activeCount++;
                else inactiveCount++;
            }
            totalAdsCount.setText(formatViews(ads.getData().size() + ""));
            totalActiveAdsCount.setText(formatViews(activeCount + ""));
            totalInActiveAdsCount.setText(formatViews(inactiveCount + ""));
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.hideShimmer();
            profileAdsPager = new ProfileAdsPager(getSupportFragmentManager(), ads);
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
