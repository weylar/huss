package com.android.huss.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.huss.R;
import com.android.huss.models.Category;
import com.android.huss.repositories.CategoryRepository;
import com.android.huss.viewModels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView catRecyclerView;
    CategoryRecycler categoryRecycler;
    RecyclerView.LayoutManager layoutManager;
    HomeViewModel homeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
            }
        });

    }

    private void generateCategoryList(List<Category> categories) {
        catRecyclerView = findViewById(R.id.recycler_cat);
        categoryRecycler = new CategoryRecycler(this, categories);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        catRecyclerView.setLayoutManager(layoutManager);
        catRecyclerView.setAdapter(categoryRecycler);
        categoryRecycler.notifyDataSetChanged();

    }
}
