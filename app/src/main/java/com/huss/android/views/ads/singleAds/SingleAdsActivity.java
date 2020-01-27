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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.huss.android.R;
import com.huss.android.models.SingleAd;
import com.huss.android.utility.Utility;
import com.huss.android.viewModels.ProfileViewModel;
import com.huss.android.viewModels.ads.AdsViewModel;
import com.huss.android.views.ads.singleAds.report.FragmentReport;
import com.huss.android.views.message.ChatActivity;
import com.huss.android.views.profile.ProfileActivity;
import com.huss.android.views.profile.UserProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.huss.android.utility.Utility.LAST_SEEN;
import static com.huss.android.utility.Utility.PHONE;
import static com.huss.android.utility.Utility.PRODUCT;
import static com.huss.android.utility.Utility.PRODUCT_ID;
import static com.huss.android.utility.Utility.PROFILE_IMAGE_URL;
import static com.huss.android.utility.Utility.USER_ID;
import static com.huss.android.utility.Utility.USER_NAME;

public class SingleAdsActivity extends AppCompatActivity {

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
    String id, name, token, ownerId;
    TextView similarLabel;
    CardView cardView;
    ImageView makeCall, message;
    ArrayList<String> allUrl = new ArrayList<>();
    private String phoneNumber, userId, productDetails, userName, lastSeen, profileImageUrl;

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
        makeCall = findViewById(R.id.make_call);
        message = findViewById(R.id.message_icon);
        shimmerImage = findViewById(R.id.shimmer_image);
        viewPager = findViewById(R.id.viewPagerAds);
        root = findViewById(R.id.root);
        root.setVisibility(View.GONE);
        similarAdsRecycler = findViewById(R.id.similarAdsRecycler);
        id = getIntent().getStringExtra(ID);
        token = getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.TOKEN, "");
        ownerId = getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(USER_ID, "");
        name = getIntent().getStringExtra(NAME);
        similarLabel = findViewById(R.id.similar_label);
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.initSingleAd(token, id);
        adsViewModel.getSingleAd().observe(this, ads -> {
            generateAds(ads.getData());
            productDetails = ads.getData().getTitle() + " (" + ads.getData().getPrice() + ")";
            generateSimilarAds(ads.getData().getSimilarAds());
            shimmerImage.hideShimmer();
            shimmerImage.setVisibility(View.GONE);
            root.setVisibility(View.VISIBLE);

        });

        if (userId.equals(ownerId)) {
            makeCall.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
        }

    }

    public void goBack(View view) {
        finish();
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
        userId = ads.getUserId().toString();
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init(token, userId);
        profileViewModel.getProfile().observe(this, profile -> {
            userName = String.format("%s %s", profile.getData().getFirstName(), profile.getData().getLastName());
            username.setText(userName);
            if (profile.getData().isOnline()) {
                lastseen.setText("Status: Online");
                lastSeen = "Online";
            } else {
                lastSeen = String.format("Last seen, %s", profile.getData().getLastSeen());
                lastseen.setText(lastSeen);
            }
            profileImageUrl = profile.getData().getProfileImgUrl();
            registered.setText(String.format("Registered, %s", formatDate(profile.getData().getCreatedAt())));
            phoneNumber = profile.getData().getPhoneNumber();
            if (phoneNumber == null) phone.setImageResource(R.drawable.no_small_phone_);
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.build().load(profileImageUrl)
                    .into(circleImageView);
        });


        report.setOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            FragmentReport reportFrag = new FragmentReport();
            reportFrag.show(fm, "report_fragment");
        });


    }

    private void generateSimilarAds(List<SingleAd.SimilarAd> ads) {
        if (ads.size() < 1) {
            similarLabel.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }
        similarAdsAdapter = new SimilarAdsAdapter(this, ads);
        layoutManagerSimilarAds = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        similarAdsRecycler.setLayoutManager(layoutManagerSimilarAds);
        similarAdsRecycler.setAdapter(similarAdsAdapter);


    }


    public void text(View view) {
        if (!userId.equals(ownerId)) {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(PRODUCT, productDetails);
            intent.putExtra(PRODUCT_ID, id);
            intent.putExtra(USER_NAME, userName);
            intent.putExtra(USER_ID, userId);
            intent.putExtra(LAST_SEEN, lastSeen);
            intent.putExtra(PROFILE_IMAGE_URL, profileImageUrl);
            startActivity(intent);
        }
    }

    public void makeCall(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        try {
            startActivity(i);
        } catch (SecurityException s) {
            Toast.makeText(this, s.getMessage(), Toast.LENGTH_LONG).show();
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
        if (userId.equals(ownerId)) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra(Utility.USER_ID, userId);
            intent.putExtra(PROFILE_IMAGE_URL, profileImageUrl);
            intent.putExtra(USER_NAME, userName);
            intent.putExtra(PHONE, phoneNumber);
            startActivity(intent);
        }
    }
}
