package com.android.huss.views.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.models.Category;
import com.android.huss.viewModels.HomeViewModel;
import com.ldoublem.loadingviewlib.view.LVGearsTwo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView catRecyclerView;
    RecyclerView topAdsRecyclerView;
    CategoryRecycler categoryRecycler;
    RecyclerView.LayoutManager layoutManager;
    HomeViewModel homeViewModel;
    LVGearsTwo progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        progressBar.setViewColor(getResources().getColor(R.color.gray));
        progressBar.startAnim(10);


    }

    @Override
    protected void onStart() {
        super.onStart();
        final ArrayList<Category> articleArrayList = new ArrayList<>();
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> catResponse) {
                articleArrayList.addAll(catResponse);

                MainActivity.this.generateCategoryList(articleArrayList);
                progressBar.stopAnim();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void generateCategoryList(List<Category> categories) {
        catRecyclerView = findViewById(R.id.recycler_cat);
        categoryRecycler = new CategoryRecycler(this, categories);
        catRecyclerView.setLayoutManager(layoutManager);
        catRecyclerView.setAdapter(categoryRecycler);
        categoryRecycler.notifyDataSetChanged();

    }

    private void generateTopAdsList(List<Ads> ads){
        topAdsRecyclerView = findViewById(R.id.recycler_top_ads);
        topAdsRecyclerView.setLayoutManager(layoutManager);
        topAdsRecyclerView.setAdapter();

    }
}
