package com.android.huss.views.favorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.FavoriteViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

public class Favorite extends AppCompatActivity {
RecyclerView recyclerView;
FavoriteViewModel favoriteViewModel;
LVCircularZoom progressBar;
FavoriteAdapter favoriteAdapter;
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
                Snackbar snackbar = Snackbar.make(progressBar, "Ad has been removed from favorites", Snackbar.LENGTH_LONG);
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
