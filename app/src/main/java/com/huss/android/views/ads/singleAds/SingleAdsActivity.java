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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.huss.android.R;
import com.huss.android.models.SingleAd;
import com.huss.android.utility.TimeFormat;
import com.huss.android.utility.Utility;
import com.huss.android.viewModels.FavoriteViewModel;
import com.huss.android.viewModels.ProfileViewModel;
import com.huss.android.viewModels.ads.AdsViewModel;
import com.huss.android.views.ads.singleAds.report.FragmentReportAd;
import com.huss.android.views.message.ChatActivity;
import com.huss.android.views.profile.ProfileActivity;
import com.huss.android.views.profile.UserProfileActivity;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.huss.android.utility.TimeFormat.formatDBTimeToLongMilli;
import static com.huss.android.utility.Utility.LAST_SEEN;
import static com.huss.android.utility.Utility.PHONE;
import static com.huss.android.utility.Utility.PRODUCT;
import static com.huss.android.utility.Utility.PRODUCT_ID;
import static com.huss.android.utility.Utility.PROFILE_IMAGE_URL;
import static com.huss.android.utility.Utility.TOKEN;
import static com.huss.android.utility.Utility.USER_ID;
import static com.huss.android.utility.Utility.USER_NAME;
import static com.huss.android.views.home.MainActivity.checkLoggedIn;

public class SingleAdsActivity extends AppCompatActivity {

    public static final String ID = "AdsId";
    public static final String NAME = "Name";
    ViewPager viewPager;
    SparkButton sparkButton;
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
        sparkButton = findViewById(R.id.favorite);
        similarAdsRecycler = findViewById(R.id.similarAdsRecycler);
        id = getIntent().getStringExtra(ID);
        token = getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(Utility.TOKEN, "");
        ownerId = getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE).getString(USER_ID, "");
        name = getIntent().getStringExtra(NAME);
        similarLabel = findViewById(R.id.similar_label);
        adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);
        adsViewModel.getSingleAd(token, id).observe(this, ads -> {
            generateAds(ads.getData());
            favorite(ads.getData().getFavorite());
            userId = ads.getData().getUserId().toString();
            productDetails = ads.getData().getTitle() + " (" + ads.getData().getPrice() + ")";
            generateSimilarAds(ads.getData().getSimilarAds());
            shimmerImage.hideShimmer();
            shimmerImage.setVisibility(View.GONE);
            root.setVisibility(View.VISIBLE);

            if (checkLoggedIn(this)) {
                if (userId.equals(ownerId)) {
                    makeCall.setVisibility(View.GONE);
                    message.setVisibility(View.GONE);
                }
            }else{
                sparkButton.setVisibility(View.GONE);
            }
        });




    }

    public void goBack(View view) {
        finish();
    }

    private void favorite(boolean isFavorite){

        FavoriteViewModel favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        sparkButton.setChecked(isFavorite);
        sparkButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState){
                favoriteViewModel.favoriteAd(token, id);
                }else{
                    favoriteViewModel.unFavoriteAd(token, id);
                }

            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

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
        getPosterInfo(circleImageView, username, lastseen, registered, phone);


        report.setOnClickListener(v -> {
            if (checkLoggedIn(this)) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentReportAd reportFrag = new FragmentReportAd();
                Bundle bundle = new Bundle();
                bundle.putString(TOKEN, token);
                bundle.putString(ID, id);
                bundle.putString(USER_ID, userId);
                reportFrag.setArguments(bundle);
                reportFrag.show(fm, "report_fragment");
            }else{
                loginWarning(v);
            }
        });


    }

    private void loginWarning(View v) {
        Snackbar.make(v, "You must login to perform this action!", Snackbar.LENGTH_LONG).show();
    }

    private void getPosterInfo(CircleImageView circleImageView, TextView username, TextView lastseen, TextView registered, ImageView phone) {
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init(token, userId);
        profileViewModel.getProfile().observe(this, profile -> {
            userName = String.format("%s %s", profile.getData().getFirstName(), profile.getData().getLastName());
            username.setText(userName);
            if (profile.getData().isOnline()) {
                lastseen.setText(R.string.online_status);
                lastSeen = "Online";
            } else {
               String formatTime = TimeFormat.getTimeAgo(formatDBTimeToLongMilli(profile.getData().getLastSeen()));
                lastSeen = String.format("Last seen, %s", formatTime);
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
        if (checkLoggedIn(this)) {
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
        }else{
            loginWarning(view);
        }
    }

    public void makeCall(View view) {
        if (checkLoggedIn(this)) {
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            try {
                startActivity(i);
            } catch (SecurityException s) {
                Toast.makeText(this, s.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            loginWarning(view);
        }
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
