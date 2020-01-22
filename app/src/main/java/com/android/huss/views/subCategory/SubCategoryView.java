package com.android.huss.views.subCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.huss.R;

import com.android.huss.models.SubCategory;
import com.android.huss.viewModels.SubCategoryViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

import static com.android.huss.utility.Utility.MY_PREFERENCES;
import static com.android.huss.utility.Utility.TOKEN;
import static com.android.huss.views.ads.singleAds.SingleAds.NAME;
import static com.android.huss.views.home.MainActivity.checkNetworkConnection;

public class SubCategoryView extends AppCompatActivity {
    RecyclerView subCategoryRecycler;
    SubCategoryViewModel subCategoryViewModel;
    ShimmerFrameLayout shimmerFrameLayout;
    SubCategoryAdapter subCategoryAdapter;
    LinearLayoutManager linearLayoutManager;
    TextView pageTitle;
    String catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        subCategoryRecycler = findViewById(R.id.sub_cat_recycler);
        pageTitle = findViewById(R.id.page_title);
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
        catName = getIntent().getStringExtra(NAME);
        pageTitle.setText(catName);
        subCategoryViewModel = ViewModelProviders.of(this).get(SubCategoryViewModel.class);
        String token = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "");
        subCategoryViewModel.init(token, catName);
        subCategoryViewModel.getSubCategory().observe(this, ads -> {
            SubCategoryView.this.generateSubcategory(ads, catName);
            shimmerFrameLayout.setVisibility(View.GONE);
        });
    }

    private void generateSubcategory(SubCategory ad, String category){
        subCategoryRecycler.setVisibility(View.VISIBLE);
        subCategoryAdapter = new SubCategoryAdapter(this, ad.getData(), category);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        subCategoryRecycler.setLayoutManager(linearLayoutManager);
        subCategoryRecycler.setAdapter(subCategoryAdapter);

    }

    public void goBack(View view){
        finish();
    }

}
