package com.android.huss.views.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.huss.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    ViewPager vpPager;
    TabLayout tabLayout;
    ProfileAdsPager profileAdsPager;
    TextView totalAdsCount, totalActiveAdsCount, totalInActiveAdsCount, name, phone, email;
    CircleImageView profileImage;

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
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        profileAdsPager = new ProfileAdsPager(getSupportFragmentManager());
        vpPager.setAdapter(profileAdsPager);
        tabLayout.setupWithViewPager(vpPager);
    }

    public void goBack (View view){
        finish();
    }
    public void openSetting(View view){
        /*TODO: Open settings*/
    }



}
