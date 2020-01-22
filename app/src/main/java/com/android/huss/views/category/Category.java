package com.android.huss.views.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.CategoryViewModel;
import com.android.huss.views.home.MainActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

import static com.android.huss.utility.Utility.MY_PREFERENCES;
import static com.android.huss.utility.Utility.TOKEN;
import static com.android.huss.utility.Utility.USER_NAME;
import static com.android.huss.views.home.MainActivity.checkNetworkConnection;

public class Category extends AppCompatActivity {
    GridLayoutManager gridLayout;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    CategoryAllAdapter categoryAdapter;
    CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkNetworkConnection(this, shimmerFrameLayout), filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.initAllCategory(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, ""));
        categoryViewModel.getAllCategory().observe(this, catResponse -> {
            Category.this.generateCategory(catResponse);
            shimmerFrameLayout.setVisibility(View.GONE);
        });
    }

    private void generateCategory(com.android.huss.models.Category category) {
        gridLayout = new GridLayoutManager(this, 4);
        recyclerView = findViewById(R.id.cat_recycler);
        recyclerView.setVisibility(View.VISIBLE);
        categoryAdapter = new CategoryAllAdapter(this, category.getData());
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(gridLayout);

    }

    public void goBack(View view) {
        finish();
    }


}
