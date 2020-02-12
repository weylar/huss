package com.huss.android.views.favorite;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.huss.android.R;
import com.huss.android.models.Ads;
import com.huss.android.models.AllAds;
import com.huss.android.models.FavoriteAd;
import com.huss.android.utility.NetworkReceiverUtil;
import com.huss.android.viewModels.FavoriteViewModel;
import com.huss.android.viewModels.ads.AdsViewModel;
import com.huss.android.views.home.MainActivity;

import java.util.List;

import static com.huss.android.utility.Utility.MY_PREFERENCES;
import static com.huss.android.utility.Utility.TOKEN;

public class FavoriteActivity extends AppCompatActivity implements NetworkReceiverUtil.ConnectivityReceiverListener {
    RecyclerView recyclerView;
    FavoriteViewModel favoriteViewModel;
    ShimmerFrameLayout shimmerFrameLayout;
    FavoriteAdapter favoriteAdapter;
    LinearLayoutManager layoutManager;
    NetworkReceiverUtil networkReceiverUtil;
    private Snackbar snackbar = null;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkReceiverUtil = new NetworkReceiverUtil();
        registerReceiver(networkReceiverUtil, filter);
        token = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "");

    }


    @Override
    protected void onStart() {
        super.onStart();


        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getMyFavoriteAds(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "") );
        favoriteViewModel.getFavAds().observe(this, favoriteAds -> {

//                generateFavoriteAds(favoriteAds);


            shimmerFrameLayout.setVisibility(View.GONE);
        });
    }

    public void goBack(View view) {
        finish();
    }


    private void generateFavoriteAds(AllAds ads) {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.favorite_recycler);
//        favoriteAdapter = new FavoriteAdapter(this, ads);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoriteAdapter);
        favoriteAdapter.notifyDataSetChanged();
        enableSwipeToDeleteAndUndo();

    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final Ads item = favoriteAdapter.getData().get(position);
                favoriteAdapter.removeItem(position);
                /*TODO: Remove from db*/
                Snackbar snackbar = Snackbar.make(shimmerFrameLayout, "Ad has been removed from favorites", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", view -> {

                    favoriteAdapter.restoreItem(item, position);
                    recyclerView.scrollToPosition(position);
                });

                snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
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
