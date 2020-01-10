package com.android.huss.views.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.models.Category;
import com.android.huss.utility.NetworkReceiverUtil;
import com.android.huss.viewModels.AdsViewModel;
import com.android.huss.viewModels.CategoryViewModel;
import com.android.huss.views.ads.createAds.CreateAds;
import com.android.huss.views.auth.LoginActivity;
import com.android.huss.views.category.CategoryAdapter;
import com.android.huss.views.favorite.Favorite;
import com.android.huss.views.ads.latestAds.LatestAds;
import com.android.huss.views.profile.Profile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.huss.utility.Utility.DEFAULT_IMAGE;
import static com.android.huss.utility.Utility.MY_PREFERENCES;
import static com.android.huss.utility.Utility.PROFILE_IMAGE_URL;
import static com.android.huss.utility.Utility.TOKEN;
import static com.android.huss.utility.Utility.USER_NAME;
import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class MainActivity extends AppCompatActivity {
    RecyclerView catRecyclerView;
    RecyclerView latestAdsRecyclerView;
    RecyclerView topAdsRecyclerView;
    CategoryAdapter categoryAdapter;
    TopAdsAdapter topAdsAdapter;
    LatestAdsAdapter latestAdsAdapter;
    RecyclerView.LayoutManager layoutManagerCat, layoutManagerTop, layoutManagerLatestAds;
    CategoryViewModel categoryViewModel;
    AdsViewModel adsViewModel;
    LVCircularZoom progressBar;
    LVCircularZoom progressBarTop;
    LVCircularZoom progressBarLatestAds;
    CircleImageView profileImage;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLoggedIn();
        fab = findViewById(R.id.fab);
        profileImage = findViewById(R.id.profileImage);
        progressBar = findViewById(R.id.progress);
        progressBarTop = findViewById(R.id.progressTop);
        progressBarLatestAds = findViewById(R.id.progressLatestAds);
        progressBar.setViewColor(getResources().getColor(R.color.gray));
        progressBarTop.setViewColor(getResources().getColor(R.color.gray));
        progressBarLatestAds.setViewColor(getResources().getColor(R.color.gray));
        progressBar.startAnim(100);
        progressBarTop.startAnim(100);
        progressBarLatestAds.startAnim(100);

        fab.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateAds.class));
        });

        loadProfileImage(profileImage, getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(PROFILE_IMAGE_URL,
                DEFAULT_IMAGE));


        drawerLayout = findViewById(R.id.activity_main);
        Toolbar toolbar = findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.nv);
        View view = navigationView.getHeaderView(0);
        ImageView imageView = view.findViewById(R.id.profile_img);
        TextView textView = view.findViewById(R.id.profileName);
        TextView button = view.findViewById(R.id.logout);
        button.setOnClickListener(v -> {
            logout();
        });
        textView.setText(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(USER_NAME, ""));
        loadProfileImage(imageView, getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(PROFILE_IMAGE_URL,
                DEFAULT_IMAGE));
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.home:
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    break;
                case R.id.favorite:
                   startActivity(new Intent(MainActivity.this, Favorite.class));
                    break;
                case R.id.messages:
                    Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.setting:
                    Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.share:
                    Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.send:
                    Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.developer:
                    Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return true;
            }


            return true;

        });


    }

    private void checkLoggedIn() {
        if(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "").equals("")){
            Intent intent =  new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void loadProfileImage(View imageview, String url) {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.build().load(url)
                .placeholder((R.drawable.image_placeholder))
                .into((ImageView) imageview);
    }

    private void logout(){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).edit().clear();
        editor.apply();
        Intent intent =  new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void moreLatest(View view) {
        Intent intent = new Intent(this, LatestAds.class);
        intent.putExtra(NAME, "Latest Ads");
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*Get all categories using view model*/
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.init();
        categoryViewModel.getCategory().observe(this, catResponse -> {
            MainActivity.this.generateCategoryList(catResponse);
            progressBar.stopAnim();
            progressBar.setVisibility(View.GONE);
            progressBar.stopAnim();
        });
        /*Get top ads using view model*/
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.init();
        adsViewModel.getAds().observe(this, ads -> {
            MainActivity.this.generateTopAdsList(ads);
            progressBarTop.stopAnim();
            progressBarTop.setVisibility(View.GONE);
            progressBarTop.stopAnim();
        });

        /*Get latest ads using view model*/
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.init();
        adsViewModel.getAds().observe(this, ads -> {
            MainActivity.this.generateLatestAdsList(ads);
            progressBarLatestAds.stopAnim();
            progressBarLatestAds.setVisibility(View.GONE);
            progressBarLatestAds.stopAnim();
        });

    }

    private void generateCategoryList(List<Category> categories) {
        catRecyclerView = findViewById(R.id.recycler_cat);
        categoryAdapter = new CategoryAdapter(this, categories);
        layoutManagerCat = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        catRecyclerView.setLayoutManager(layoutManagerCat);
        catRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }

    private void generateTopAdsList(List<Ads> ads) {
        topAdsRecyclerView = findViewById(R.id.recycler_top_ads);
        topAdsAdapter = new TopAdsAdapter(this, ads);
        layoutManagerTop = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topAdsRecyclerView.setLayoutManager(layoutManagerTop);
        topAdsRecyclerView.setAdapter(topAdsAdapter);

    }

    private void generateLatestAdsList(List<Ads> ads) {
        latestAdsRecyclerView = findViewById(R.id.recycler_latest_ads);
        latestAdsAdapter = new LatestAdsAdapter(this, ads);
        layoutManagerLatestAds = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        latestAdsRecyclerView.setLayoutManager(layoutManagerLatestAds);
        latestAdsRecyclerView.setAdapter(latestAdsAdapter);


    }

    public void openProfile (View view){
        startActivity(new Intent(this, Profile.class));
    }

    public void allCategory(View view) {
        startActivity(new Intent(this, com.android.huss.views.category.Category.class));
    }
}
