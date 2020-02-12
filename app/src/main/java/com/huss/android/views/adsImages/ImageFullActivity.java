package com.huss.android.views.adsImages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.huss.android.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class ImageFullActivity extends AppCompatActivity {
    public static final String PAGE = "page";
    FullAdsPager pager;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);
        viewPager = findViewById(R.id.viewPagerFull);
        pager = new FullAdsPager(this, getAllUrl());
        viewPager.setAdapter(pager);
        viewPager.setCurrentItem(getIntent().getIntExtra(PAGE, 0));
        TabLayout tabLayout = findViewById(R.id.tabDotsFull);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    ArrayList<String> allUrl = new ArrayList<>();
    public ArrayList<String> getAllUrl() {
        allUrl.addAll(Objects.requireNonNull(getIntent().getStringArrayListExtra("URL")));
        return allUrl;
    }

    public void goBack(View view) {
        finish();
    }
}
