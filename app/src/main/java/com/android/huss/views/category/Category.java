package com.android.huss.views.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.CategoryViewModel;
import com.android.huss.views.home.MainActivity;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

public class Category extends AppCompatActivity {
    GridLayoutManager gridLayout;
    RecyclerView recyclerView;
    LVCircularZoom lvCircularZoom;
    CategoryAdapter categoryAdapter;
    CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        lvCircularZoom = findViewById(R.id.progress);
        lvCircularZoom.setViewColor(getResources().getColor(R.color.gray));
        lvCircularZoom.startAnim(100);
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.init();
        categoryViewModel.getCategory().observe(this, catResponse -> {
            Category.this.generateCategory(catResponse);
            lvCircularZoom.stopAnim();
            lvCircularZoom.setVisibility(View.GONE);
            lvCircularZoom.stopAnim();
        });
    }

    private void generateCategory(List<com.android.huss.models.Category> ads) {
        gridLayout = new GridLayoutManager(this, 4);
        recyclerView = findViewById(R.id.cat_recycler);
        categoryAdapter = new CategoryAdapter(this, ads);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(gridLayout);

    }

    public void goBack(View view) {
        finish();
    }
}
