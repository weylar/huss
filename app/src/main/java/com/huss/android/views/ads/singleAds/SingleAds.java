package com.huss.android.views.ads.singleAds;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huss.android.R;
import com.huss.android.models.SingleAd;
import com.huss.android.viewModels.AdsViewModel;
import com.huss.android.viewModels.ProfileViewModel;
import com.huss.android.views.ads.singleAds.report.FragmentReport;
import com.huss.android.views.message.ChatActivity;
import com.huss.android.views.profile.Profile;
import com.huss.android.views.profile.UserProfile;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.huss.android.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleAds extends AppCompatActivity {

    public static final String ID = "AdsId";
    public static final String NAME = "Name";
    ViewPager viewPager;
    RecyclerView similarAdsRecycler;
    Pager pager;
    AdsViewModel adsViewModel;
    ShimmerFrameLayout shimmerImage;
    SimilarAdsAdapter similarAdsAdapter;
    NestedScrollView root;
    RecyclerView.LayoutManager layoutManagerSimilarAds;
    String id, name, token;
    TextView similarLabel;
    CardView cardView;
    ArrayList<String> allUrl = new ArrayList<>();
    String phoneNumber, userId;

    public static String formatViews(String views) {
        String value = Integer.parseInt(views) > 1000000000 ? (Float.parseFloat(views) / 1000000000) + "B" :
                Integer.parseInt(views) > 1000000 ? (Float.parseFloat(views) / 1000000) + "M" :
                        Integer.parseInt(views) > 1000 ? (Integer.parseInt(views) / 1000) + "K" :
                                views;
        return value.endsWith(".0B") || value.endsWith(".0M") || value.endsWith(".0K")
                ? value.replace(".0", "") : value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ads);
        cardView = findViewById(R.id.card_view);
        shimmerImage = findViewById(R.id.shimmer_image);
        viewPager = findViewById(R.id.viewPagerAds);
        root = findViewById(R.id.root);
        root.setVisibility(View.GONE);
        similarAdsRecycler = findViewById(R.id.similarAdsRecycler);
        id = getIntent().getStringExtra(ID);
        token = getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.TOKEN, "");
        name = getIntent().getStringExtra(NAME);
        similarLabel = findViewById(R.id.similar_label);
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.initSingleAd(token, id);
        adsViewModel.getSingleAd().observe(this, ads -> {
            generateAds(ads.getData());
            generateSimilarAds(ads.getData().getSimilarAds());
            shimmerImage.hideShimmer();
            shimmerImage.setVisibility(View.GONE);
            root.setVisibility(View.VISIBLE);

        });

    }

    public void goBack(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }


    private void generateAds(SingleAd.Data ads) {
        CircleImageView circleImageView = findViewById(R.id.profile_img);
        TextView adsName = findViewById(R.id.adsName);
        TextView report = findViewById(R.id.report);
        TextView views = findViewById(R.id.views);
        TextView price = findViewById(R.id.price);
        TextView username = findViewById(R.id.username);
        TextView lastseen = findViewById(R.id.lastseen);
        TextView registered = findViewById(R.id.registered);
        TextView description = findViewById(R.id.description);
        TextView dateAndTime = findViewById(R.id.timeAndDate);
        ImageView phone = findViewById(R.id.make_call);

        adsName.setText(ads.getTitle());
        views.setText(String.format("%s views", formatViews(ads.getCount().toString())));
        price.setText(formatPrice(ads.getPrice()));
        description.setText(ads.getDescription());
        for (SingleAd.AdImage i : ads.getAdImages()) {
            if (i.getDisplayImage()) {
                allUrl.add(0, i.getImageUrl());
            } else {
                allUrl.add(i.getImageUrl());
            }

        }
        pager = new Pager(this, allUrl);
        viewPager.setAdapter(pager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        dateAndTime.setText(String.format("%s | %s", ads.getLocation(), formatDate(ads.getCreatedAt())));
        username.setText(getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.USER_NAME, ""));
        userId = ads.getUserId().toString();
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init(token, userId);
        profileViewModel.getProfile().observe(this, profile -> {
            if (profile.getData().isOnline()){
                lastseen.setText("Status: Online");
            }else{
                lastseen.setText(String.format("Last seen, %s", profile.getData().getLastSeen()));
            }

            registered.setText(String.format("Registered, %s", formatDate(profile.getData().getCreatedAt())));
            phoneNumber = profile.getData().getPhoneNumber();
            if (phoneNumber == null) phone.setImageResource(R.drawable.no_small_phone_);
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.build().load(profile.getData().getProfileImgUrl())
                    .into(circleImageView);
        });


        report.setOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            FragmentReport reportFrag = new FragmentReport();
            reportFrag.show(fm, "report_fragment");
        });



    }

    private void generateSimilarAds(List<SingleAd.SimilarAd> ads) {
        if (ads.size() < 1){
            similarLabel.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }
        similarAdsAdapter = new SimilarAdsAdapter(this, ads);
        layoutManagerSimilarAds = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        similarAdsRecycler.setLayoutManager(layoutManagerSimilarAds);
        similarAdsRecycler.setAdapter(similarAdsAdapter);


    }



    public void text(View view) {
       Intent intent = new Intent(this, ChatActivity.class);
       intent.putExtra(Utility.USER_ID, userId);
       startActivity(intent);
    }

    public void makeCall(View view) {
        if (phoneNumber == null){
            Snackbar snackbar = Snackbar.make(view, "You can't call this user because, phone number is not provided.",
                    Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
            snackbar.setAction("Ok", v -> snackbar.dismiss());
            snackbar.show();
        }else {
            Uri u = Uri.parse("tel:" + phoneNumber);
            Intent i = new Intent(Intent.ACTION_DIAL, u);
            try {
                startActivity(i);
            } catch (SecurityException s) {
                Toast.makeText(this, s.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void favoriteAds(View view) {

    }

    private String formatPrice(String price) {
        String value = Integer.parseInt(price) > 1000000000 ? "\u20A6" + (Float.parseFloat(price) / 1000000000) + "B" :
                Integer.parseInt(price) > 1000000 ? "\u20A6" + (Float.parseFloat(price) / 1000000) + "M" :
                        Integer.parseInt(price) > 1000 ? "\u20A6" + (Float.parseFloat(price) / 1000) + "K" :
                                "\u20A6" + price;
        return value.endsWith(".0B") || value.endsWith(".0M") || value.endsWith(".0K")
                ? value.replace(".0", "") : value;
    }

    private String formatDate(String date) {
        String dbDay = date.split("T")[0].split("-")[2];
        String dbYear = date.split("T")[0].split("-")[0];
        String dbMonth = date.split("T")[0].split("-")[1];
        String time = date.split("T")[1].split(":")[0] + ":"
                + date.split("T")[1].split(":")[1];
        String today = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String yesterday = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1);
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        return dbDay.equals(today) && dbMonth.equals(month) && dbYear.equals(year) ? "Today at " + time
                : dbDay.equals(yesterday) && dbMonth.equals(month) && dbYear.equals(year) ? "Yesterday at " + time
                : setAnyOtherDay(dbMonth, dbDay);

    }

    private String setAnyOtherDay(String dbMonth, String dbDay) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.parseInt(dbMonth) - 1);
        return String.format(Locale.US, "%tB", cal) + " " + dbDay;

    }


    public void openUserProfile(View view) {
        if (userId.equals(getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.USER_ID, ""))){
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, UserProfile.class);
            intent.putExtra(Utility.USER_ID, userId);
            startActivity(intent);
        }
    }
}
