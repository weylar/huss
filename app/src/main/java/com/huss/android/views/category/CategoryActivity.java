package com.huss.android.views.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.huss.android.R;
import com.huss.android.utility.NetworkReceiverUtil;
import com.huss.android.viewModels.category.CategoryViewModel;
import com.huss.android.views.home.MainActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.huss.android.utility.Utility;

public class CategoryActivity extends AppCompatActivity implements NetworkReceiverUtil.ConnectivityReceiverListener {
    GridLayoutManager gridLayout;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    CategoryAllAdapter categoryAdapter;
    CategoryViewModel categoryViewModel;
    NetworkReceiverUtil networkReceiverUtil;
    private Snackbar snackbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkReceiverUtil = new NetworkReceiverUtil();
        registerReceiver(networkReceiverUtil, filter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.initAllCategory(getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.TOKEN, ""));
        categoryViewModel.getAllCategory().observe(this, catResponse -> {
            CategoryActivity.this.generateCategory(catResponse);
            shimmerFrameLayout.setVisibility(View.GONE);
        });
    }

    private void generateCategory(com.huss.android.models.Category category) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiverUtil);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkReceiverUtil.Companion.setConnectivityReceiverListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected){
            snackbar = Snackbar.make(shimmerFrameLayout, "No internet connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }else{
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }
    }
}
