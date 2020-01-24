package com.android.huss.views.home;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.models.AllAds;
import com.android.huss.models.Category;
import com.android.huss.utility.NetworkReceiverUtil;
import com.android.huss.viewModels.AdsViewModel;
import com.android.huss.viewModels.CategoryViewModel;
import com.android.huss.views.ads.createAds.CreateAds;
import com.android.huss.views.ads.latestAds.LatestAds;
import com.android.huss.views.auth.LoginActivity;
import com.android.huss.views.category.CategoryAdapter;
import com.android.huss.views.favorite.Favorite;
import com.android.huss.views.profile.Profile;
import com.android.huss.views.settings.SettingsActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.android.huss.utility.Utility.CATEGORY_LIMIT;
import static com.android.huss.utility.Utility.DEFAULT_IMAGE;
import static com.android.huss.utility.Utility.KEY;
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
    CircleImageView profileImage;
    FloatingActionButton fab;
    TextView placeholder, topAdLabel;
    AutoCompleteTextView searchbox;
    ShimmerFrameLayout shimmerFrameLayout, shimmerFrameLayoutTop, shimmerFrameLayoutLatest;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    String token;

    @NotNull
    public static NetworkReceiverUtil checkNetworkConnection(Context context, View view) {

        return new NetworkReceiverUtil() {
            @Override
            protected void onNetworkChange(boolean state) {
                if (!state) {
                    Snackbar snackBar = Snackbar.make(view, "No network connection!", Snackbar.LENGTH_INDEFINITE);
                    snackBar.setAction("Ok", v -> {
                        snackBar.dismiss();
                    });
                    snackBar.setActionTextColor(context.getResources().getColor(R.color.colorAccent));
                    snackBar.show();
                }
            }

        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        topAdLabel = findViewById(R.id.top_ad_title);
        searchbox = findViewById(R.id.searchbox);
        placeholder = findViewById(R.id.placeholder);
        profileImage = findViewById(R.id.profileImage);
        shimmerFrameLayoutTop = findViewById(R.id.shimmer_view_container2);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayoutLatest = findViewById(R.id.shimmer_view_container3);
        token = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "");
        if (checkLoggedIn()) {
            suggestSearchInput();
            searchbox.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(this, LatestAds.class);
                    intent.putExtra(KEY, searchbox.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            });
            fab.setOnClickListener(v -> {
                startActivity(new Intent(this, CreateAds.class));
            });
            loadProfileImage(profileImage, placeholder, getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(PROFILE_IMAGE_URL, DEFAULT_IMAGE));
            drawerLayout = findViewById(R.id.activity_main);
            Toolbar toolbar = findViewById(R.id.homeToolBar);
            setSupportActionBar(toolbar);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
            navigationView = findViewById(R.id.nv);
            View view = navigationView.getHeaderView(0);
            ImageView imageView = view.findViewById(R.id.profile_img);
            View.OnClickListener onClickListener = v -> {
                startActivity(new Intent(this, Profile.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            };
            imageView.setOnClickListener(onClickListener);
            TextView textView = view.findViewById(R.id.profileName);
            textView.setOnClickListener(onClickListener);
            TextView button = view.findViewById(R.id.logout);
            TextView placeholder = view.findViewById(R.id.placeholder);
            button.setOnClickListener(v -> {
                logout();
            });
            textView.setText(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(USER_NAME, ""));
            loadProfileImage(imageView, placeholder, getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(PROFILE_IMAGE_URL,
                    DEFAULT_IMAGE));
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {
                    case R.id.home:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                    case R.id.favorite:
                        startActivity(new Intent(MainActivity.this, Favorite.class));
                        break;
                    case R.id.messages:

                        break;
                    case R.id.setting:
                        startActivity(new Intent(this, SettingsActivity.class));
                        break;
                    case R.id.share:

                        break;
                    case R.id.about:

                        break;

                    default:
                        return true;
                }


                return true;

            });

        }
        onLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkNetworkConnection(this, shimmerFrameLayout), filter);
    }

    private void suggestSearchInput() {
        ArrayList<String> suggestions = new ArrayList<>();
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.initAllAds(token);
        adsViewModel.getAllAds().observe(this, ads -> {
           for (AllAds.Data ad : ads.getData()){
              suggestions.add(ad.getTitle());
           }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, suggestions);
            searchbox.setAdapter(adapter);
            searchbox.setThreshold(1);
        });

        Timber.e(suggestions.toString());


    }

    private boolean checkLoggedIn() {
        if (getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "").equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
    }

    public void loadProfileImage(View imageview, TextView placeholder, String url) {
        if (url.equals(DEFAULT_IMAGE)) {
            placeholder.setVisibility(View.VISIBLE);
            imageview.setVisibility(View.GONE);
            placeholder.setText(String.format("%s", getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(USER_NAME, "").charAt(0)));
        } else {
            imageview.setVisibility(View.VISIBLE);
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.build().load(url)
                    .into((ImageView) imageview);
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).edit().clear();
        editor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void moreLatest(View view) {
        Intent intent = new Intent(this, LatestAds.class);
        intent.putExtra(NAME, "Latest Ads");
        startActivity(intent);
    }



    protected void onLoad() {

        /*Get all categories using view model*/
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.init(token);
        categoryViewModel.getPopularCategory().observe(this, val -> {
            generateCategoryList(val);
            shimmerFrameLayout.setVisibility(View.GONE);
        });
        /*Get top ads using view model*/
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.initAllAdsByLimit(token);
        adsViewModel.getAllAdsByLimit().observe(this, ads -> {
            MainActivity.this.generateTopAdsList(ads);
            shimmerFrameLayoutTop.setVisibility(View.GONE);
        });


        /*Get latest ads using view model*/
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.initAllAdsByLimit(token);
        adsViewModel.getAllAdsByLimit().observe(this, ads -> {
            MainActivity.this.generateLatestAdsList(ads);
            shimmerFrameLayoutLatest.setVisibility(View.GONE);
        });


    }

    private void generateCategoryList(Category categories) {
        catRecyclerView = findViewById(R.id.recycler_cat);
        categoryAdapter = new CategoryAdapter(this, categories.getData());
        layoutManagerCat = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        catRecyclerView.setLayoutManager(layoutManagerCat);
        catRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }

    private void generateTopAdsList(AllAds ads) {
        topAdsRecyclerView = findViewById(R.id.recycler_top_ads);
        topAdsAdapter = new TopAdsAdapter(this, ads);
        layoutManagerTop = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topAdsRecyclerView.setLayoutManager(layoutManagerTop);
        topAdsRecyclerView.setAdapter(topAdsAdapter);
        if (topAdsAdapter.getItemCount() < 1){
            topAdLabel.setVisibility(View.GONE);
        }

    }

    private void generateLatestAdsList(AllAds ads) {
        latestAdsRecyclerView = findViewById(R.id.recycler_latest_ads);
        latestAdsAdapter = new LatestAdsAdapter(this, ads);
        layoutManagerLatestAds = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        latestAdsRecyclerView.setLayoutManager(layoutManagerLatestAds);
        latestAdsRecyclerView.setAdapter(latestAdsAdapter);


    }

    public void openProfile(View view) {
        startActivity(new Intent(this, Profile.class));
    }

    public void allCategory(View view) {
        startActivity(new Intent(this, com.android.huss.views.category.Category.class));
    }
}
