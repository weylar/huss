package com.android.huss.views.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.CategoryViewModel;
import com.android.huss.viewModels.FavoriteViewModel;
import com.android.huss.views.home.CategoryAdapter;
import com.android.huss.views.home.MainActivity;
import com.android.huss.views.latestAds.AllLatestAdsAdapter;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

public class Favorite extends AppCompatActivity {
RecyclerView recyclerView;
FavoriteViewModel favoriteViewModel;
LVCircularZoom progressBar;
AllLatestAdsAdapter allLatestAdsAdapter;
LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        progressBar = findViewById(R.id.progress);
        progressBar.setViewColor(getResources().getColor(R.color.gray));
        progressBar.startAnim(100);
    }

    @Override
    protected void onStart() {
        super.onStart();


        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.init(/*TODO: Append user id*/ "");
        favoriteViewModel.getFavoriteAds().observe(this, favoriteAds -> {

            Favorite.this.generateFavoriteAds(favoriteAds);
            progressBar.stopAnim();
            progressBar.setVisibility(View.GONE);
            progressBar.stopAnim();
        });
    }

    public void goBack(View view){
        finish();
    }


    private void generateFavoriteAds(List<Ads> ads) {
        recyclerView = findViewById(R.id.favorite_recycler);
        allLatestAdsAdapter = new AllLatestAdsAdapter(this, ads);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(allLatestAdsAdapter);
        allLatestAdsAdapter.notifyDataSetChanged();

    }
}
