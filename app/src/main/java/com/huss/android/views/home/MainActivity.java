package com.huss.android.views.home;

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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.huss.android.R;
import com.huss.android.models.AllAds;
import com.huss.android.models.Category;
import com.huss.android.utility.NetworkReceiverUtil;
import com.huss.android.viewModels.ads.AdsViewModel;
import com.huss.android.viewModels.category.CategoryViewModel;
import com.huss.android.viewModels.ProfileViewModel;
import com.huss.android.views.ads.createAds.CreateAdsActivity;
import com.huss.android.views.ads.latestAds.LatestAdsActivity;
import com.huss.android.views.auth.LoginActivity;
import com.huss.android.views.category.CategoryActivity;
import com.huss.android.views.category.CategoryAdapter;
import com.huss.android.views.favorite.FavoriteActivity;
import com.huss.android.views.message.ChatActivity;
import com.huss.android.views.profile.ProfileActivity;
import com.huss.android.views.settings.SettingsActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.huss.android.utility.Utility.DEFAULT_IMAGE;
import static com.huss.android.utility.Utility.FROM;
import static com.huss.android.utility.Utility.KEY;
import static com.huss.android.utility.Utility.MY_PREFERENCES;
import static com.huss.android.utility.Utility.PROFILE_IMAGE_URL;
import static com.huss.android.utility.Utility.TOKEN;
import static com.huss.android.utility.Utility.USER_NAME;
import static com.huss.android.views.ads.singleAds.SingleAdsActivity.NAME;

public class MainActivity extends AppCompatActivity implements NetworkReceiverUtil.ConnectivityReceiverListener {
    private AdsViewModel adsViewModel;
    private TextView topAdLabel;
    private AutoCompleteTextView searchBox;
    private ShimmerFrameLayout shimmerFrameLayout, shimmerFrameLayoutTop, shimmerFrameLayoutLatest;
    private String token;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NetworkReceiverUtil networkReceiverUtil;
    Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        topAdLabel = findViewById(R.id.top_ad_title);
        searchBox = findViewById(R.id.searchbox);
        TextView placeholderSmall = findViewById(R.id.placeholder);
        CircleImageView profileImage = findViewById(R.id.profileImage);
        shimmerFrameLayoutTop = findViewById(R.id.shimmer_view_container2);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayoutLatest = findViewById(R.id.shimmer_view_container3);
        token = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "");
        String imageUrl = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(PROFILE_IMAGE_URL, DEFAULT_IMAGE);
        suggestSearchInput();
        searchBox.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent(this, LatestAdsActivity.class);
                intent.putExtra(KEY, searchBox.getText().toString());
                startActivity(intent);
                return true;
            }
            return false;
        });
        fab.setOnClickListener(v -> {
            if (checkLoggedIn(this)) {
                startActivity(new Intent(this, CreateAdsActivity.class));
            } else {
                forceLogin(CreateAdsActivity.class.getSimpleName());
            }


        });
        drawerLayout = findViewById(R.id.activity_main);
        Toolbar toolbar = findViewById(R.id.homeToolBar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nv);
        View view = navigationView.getHeaderView(0);
        ImageView imageView = view.findViewById(R.id.profile_img);
        View.OnClickListener onClickListener = v -> {
            startActivity(new Intent(this, ProfileActivity.class));
            drawerLayout.closeDrawer(GravityCompat.START);
        };
        imageView.setOnClickListener(onClickListener);
        TextView textView = view.findViewById(R.id.profileName);
        textView.setOnClickListener(onClickListener);
        TextView button = view.findViewById(R.id.logout);
        TextView placeholder = view.findViewById(R.id.placeholder);
        if (checkLoggedIn(this)) {
            button.setOnClickListener(v -> {
                drawerLayout.closeDrawer(GravityCompat.START);
                logout();

            });
        } else {
            button.setOnClickListener(v -> {
                drawerLayout.closeDrawer(GravityCompat.START);
                forceLogin(MainActivity.class.getSimpleName());


            });
            button.setText(getResources().getString(R.string.sign_in));
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            textView.setText(getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(USER_NAME, ""));
        }
        loadProfileImage(profileImage, placeholderSmall, imageUrl);
        loadProfileImage(imageView, placeholder, imageUrl);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (id) {
                case R.id.home:
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                    break;
                case R.id.favorite:
                    startActivity(new Intent(MainActivity.this, FavoriteActivity.class));

                    break;
                case R.id.messages:
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));
                    break;
                case R.id.setting:
                    startActivity(new Intent(this, SettingsActivity.class));

                    break;
                case R.id.share:


                    break;
                case R.id.about:
                    startActivity(new Intent(this, AboutActivity.class));
                    break;

                default:
                    return true;
            }


            return true;

        });
        onLoad();
        updateOnlineStatus(true);

        networkReceiverUtil = new NetworkReceiverUtil();
        registerReceiver(networkReceiverUtil, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));



    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkReceiverUtil.Companion.setConnectivityReceiverListener(this);
    }

    private void updateOnlineStatus(boolean isOnline) {
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.initLastSeen(token, isOnline);
        profileViewModel.updateLastSeen().observe(this, v -> {
        });
    }


    private void suggestSearchInput() {
        ArrayList<String> suggestions = new ArrayList<>();
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.initAllAds(token);
        adsViewModel.getAllAds().observe(this, ads -> {
            for (AllAds.Data ad : ads.getData()) {
                suggestions.add(ad.getTitle());
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, suggestions);
            searchBox.setAdapter(adapter);
            searchBox.setThreshold(1);
        });



    }

    public static boolean checkLoggedIn(Context context) {
        return !context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(TOKEN, "").equals("");
    }

    private void forceLogin(String from) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(FROM, from);
        startActivity(intent);
    }

    public void loadProfileImage(View imageview, TextView placeholder, String url) {
        if (url.equals(DEFAULT_IMAGE)) {
            if (checkLoggedIn(this)){
                placeholder.setVisibility(View.VISIBLE);
                imageview.setVisibility(View.GONE);
                placeholder.setText(String.format("%s", getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).getString(USER_NAME, "").charAt(0)));
            }else{
                placeholder.setVisibility(View.VISIBLE);
                imageview.setVisibility(View.GONE);
                placeholder.setText(String.format("%s", "H"));
            }

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
        forceLogin(MainActivity.class.getSimpleName());
    }


    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void moreLatest(View view) {
        Intent intent = new Intent(this, LatestAdsActivity.class);
        intent.putExtra(NAME, "Latest Ads");
        startActivity(intent);
    }

    protected void onLoad() {

        /*Get all categories using view model*/
        CategoryViewModel categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
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
        RecyclerView catRecyclerView = findViewById(R.id.recycler_cat);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories.getData());
        RecyclerView.LayoutManager layoutManagerCat = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        catRecyclerView.setLayoutManager(layoutManagerCat);
        catRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }

    private void generateTopAdsList(AllAds ads) {
        RecyclerView topAdsRecyclerView = findViewById(R.id.recycler_top_ads);
        TopAdsAdapter topAdsAdapter = new TopAdsAdapter(this, ads);
        RecyclerView.LayoutManager layoutManagerTop = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topAdsRecyclerView.setLayoutManager(layoutManagerTop);
        topAdsRecyclerView.setAdapter(topAdsAdapter);
        if (topAdsAdapter.getItemCount() < 1) {
            topAdLabel.setVisibility(View.GONE);
        }

    }

    private void generateLatestAdsList(AllAds ads) {
        RecyclerView latestAdsRecyclerView = findViewById(R.id.recycler_latest_ads);
        LatestAdsAdapter latestAdsAdapter = new LatestAdsAdapter(this, ads);
        RecyclerView.LayoutManager layoutManagerLatestAds = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        latestAdsRecyclerView.setLayoutManager(layoutManagerLatestAds);
        latestAdsRecyclerView.setAdapter(latestAdsAdapter);


    }

    public void openProfile(View view) {
        if (checkLoggedIn(this)){
            startActivity(new Intent(this, ProfileActivity.class));
        }else{
            forceLogin(ProfileActivity.class.getSimpleName());
        }

    }

    public void allCategory(View view) {
        startActivity(new Intent(this, CategoryActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateOnlineStatus(false);
        unregisterReceiver(networkReceiverUtil);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected){
             snackbar = Snackbar.make(topAdLabel, "No internet connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }else{
           if (snackbar != null) {
               snackbar.dismiss();
           }
        }
    }
}
