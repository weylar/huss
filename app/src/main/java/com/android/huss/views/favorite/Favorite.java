package com.android.huss.views.favorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.FavoriteViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

import static com.android.huss.views.home.MainActivity.checkNetworkConnection;

public class Favorite extends AppCompatActivity {
RecyclerView recyclerView;
FavoriteViewModel favoriteViewModel;
ShimmerFrameLayout shimmerFrameLayout;
FavoriteAdapter favoriteAdapter;
LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
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


        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.init(/*TODO: Append user id*/ "");
        favoriteViewModel.getFavoriteAds().observe(this, favoriteAds -> {

            Favorite.this.generateFavoriteAds(favoriteAds);
            shimmerFrameLayout.setVisibility(View.GONE);
        });
    }

    public void goBack(View view){
        finish();
    }


    private void generateFavoriteAds(List<Ads> ads) {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.favorite_recycler);
        favoriteAdapter = new FavoriteAdapter(this, ads);
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



}
