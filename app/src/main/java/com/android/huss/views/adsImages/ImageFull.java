package com.android.huss.views.adsImages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.android.huss.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ImageFull extends AppCompatActivity {
    public static final String PAGE = "page";
    FullAdsPager pager;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);
        viewPager = findViewById(R.id.viewPagerFull);

        pager = new FullAdsPager(this, getAllUrl() /*ads.getAllImgUrl()*/);
        viewPager.setCurrentItem(getIntent().getIntExtra(PAGE, 0));
        viewPager.setAdapter(pager);
        TabLayout tabLayout = findViewById(R.id.tabDotsFull);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    ArrayList<String> allUrl = new ArrayList<>();

    public ArrayList<String> getAllUrl() {
        allUrl.add("https://via.placeholder.com/600/771796");
        allUrl.add("https://randomuser.me/api/portraits/men/58.jpg");
        allUrl.add("https://via.placeholder.com/600/771796");
        allUrl.add("https://via.placeholder.com/600/24f355");
        allUrl.add("https://via.placeholder.com/600/f66b97");
        return allUrl;
    }

    public void goBack(View view) {
        finish();
    }
}
