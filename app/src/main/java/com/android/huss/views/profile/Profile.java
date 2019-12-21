package com.android.huss.views.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.android.huss.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class Profile extends AppCompatActivity {
    ViewPager vpPager;
    TabLayout tabLayout;
    ProfileAdsPager profileAdsPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        vpPager = findViewById(R.id.viewPagerProfile);
        tabLayout = findViewById(R.id.tabLayoutProfile);
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
