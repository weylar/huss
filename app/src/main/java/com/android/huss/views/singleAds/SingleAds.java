package com.android.huss.views.singleAds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.AdsViewModel;
import com.android.huss.viewModels.SimilarAdsViewModel;
import com.android.huss.viewModels.SingleAdsViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleAds extends AppCompatActivity {

    ViewPager viewPager;
    RecyclerView similarAdsRecycler;
    Pager pager;
    SingleAdsViewModel adsViewModel;
    SimilarAdsViewModel similarAdsViewModel;
    LVCircularZoom progressBarLatestAds;
    SimilarAdsAdapter similarAdsAdapter;
    RecyclerView.LayoutManager layoutManagerSimilarAds;
    String id, name;
    public static final String ID = "AdsId";
    public static final String NAME = "Name";
    TextView offerPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ads);
        viewPager = findViewById(R.id.viewPagerAds);
        progressBarLatestAds = findViewById(R.id.progress);
        similarAdsRecycler = findViewById(R.id.similarAdsRecycler);
        progressBarLatestAds.setViewColor(getResources().getColor(R.color.colorAccent));
        progressBarLatestAds.startAnim(100);
        id = getIntent().getStringExtra(ID);
        name = getIntent().getStringExtra(NAME);


    }

    public void goBack(View view) {
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*Get latest ads using view model*/
        adsViewModel = ViewModelProviders.of(this).get(SingleAdsViewModel.class);
        adsViewModel.init(id);
        adsViewModel.getSingleAds().observe(this, ads -> {
            ads.setTitle("iPhoneX Max");
            ads.setPrice("2");
            ads.setLocation("Abuja");
            ads.setViews("2000000000");
            ads.setCreatedAt("2019-12-15T06:55:18.277Z");
            ads.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            SingleAds.this.generateAds(ads);
            progressBarLatestAds.stopAnim();
            progressBarLatestAds.setVisibility(View.GONE);
            progressBarLatestAds.stopAnim();
        });

        /*Get latest ads using view model*/
        similarAdsViewModel = ViewModelProviders.of(this).get(SimilarAdsViewModel.class);
        similarAdsViewModel.init(name);
        similarAdsViewModel.getSimilarAds().observe(this, SingleAds.this::generateSimilarAds);
}

    ArrayList<String> allUrl = new ArrayList<>();

    public ArrayList<String> getAllUrl() {
        allUrl.add( "https://via.placeholder.com/600/771796");
        allUrl.add("https://randomuser.me/api/portraits/men/58.jpg");
        allUrl.add( "https://via.placeholder.com/600/771796");
        allUrl.add("https://via.placeholder.com/600/24f355");
        allUrl.add("https://via.placeholder.com/600/f66b97");
        return allUrl;
    }

    private void generateAds(Ads ads) {
        CircleImageView circleImageView = findViewById(R.id.profile_img);
        TextView adsName = findViewById(R.id.adsName);
        TextView report = findViewById(R.id.report);
        TextView views = findViewById(R.id.views);
        TextView price = findViewById(R.id.price);
        TextView username = findViewById(R.id.username);
        TextView lastseen = findViewById(R.id.lastseen);
        offerPrice = findViewById(R.id.offerPrice);
        TextView description = findViewById(R.id.description);
        TextView dateAndTime = findViewById(R.id.timeAndDate);
        adsName.setText(ads.getTitle());
        views.setText(formatViews(ads.getViews()));
        price.setText(formatPrice(ads.getPrice()));
        description.setText(ads.getDescription());
        dateAndTime.setText(ads.getLocation() + ", " + formatDate(ads.getCreatedAt()));
//        TODO: Get the username,lastseen, imgUrl from sharedPreference class
        username.setText("Aminu Idris");
        lastseen.setText("Today, at 6:30PM");
        offerPrice.setText( formatPrice(String.valueOf(Integer.parseInt(ads.getPrice()) / 2)));
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.build().load("https://randomuser.me/api/portraits/women/5.jpg")
                .placeholder((R.drawable.sample))
                .error(R.drawable.flag)
                .into(circleImageView);


        pager = new Pager(this, getAllUrl() /*ads.getAllImgUrl()*/);
        viewPager.setAdapter(pager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        report.setOnClickListener(v -> {
            /*TODO: Send to db report*/
            Toast.makeText(this, "Ad already reported, thanks for the feedback", Toast.LENGTH_LONG).show();
        });

    }

    private void generateSimilarAds(List<Ads> ads) {
        /*Load Similar Ads*/
        similarAdsAdapter = new SimilarAdsAdapter(SingleAds.this, ads);
        layoutManagerSimilarAds = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        similarAdsRecycler.setLayoutManager(layoutManagerSimilarAds);
        similarAdsRecycler.setAdapter(similarAdsAdapter);


    }

    public void bid(View view){
//        TODO: Raise bids. Check if not less than half the original price
        Toast.makeText(this, offerPrice.getText().toString().replace("\u20A6", ""), Toast.LENGTH_SHORT).show();
    }
    private String formatPrice(String price){
        String value = Integer.parseInt(price) > 1000000000 ? "\u20A6" + (Float.parseFloat(price) / 1000000000) + "B" :
                Integer.parseInt(price) > 1000000  ? "\u20A6" + (Float.parseFloat(price) / 1000000) + "M" :
                        Integer.parseInt(price) > 1000  ? "\u20A6" + (Float.parseFloat(price) / 1000) + "K" :
                        "\u20A6" + price;
        return value.endsWith(".0B") || value.endsWith(".0M") || value.endsWith(".0K")
                ? value.replace(".0", ""): value;
    }
    private String formatViews(String views){
        String value = Integer.parseInt(views) > 1000000000 ? (Float.parseFloat(views) / 1000000000) + "B" :
                Integer.parseInt(views) > 1000000 ? (Float.parseFloat(views) / 1000000) + "M" :
                        Integer.parseInt(views) > 1000 ? (Float.parseFloat(views) / 1000) + "K" :
                         views;
        return value.endsWith(".0B") || value.endsWith(".0M") || value.endsWith(".0K")
                ? value.replace(".0", ""): value;
    }
    private String formatDate(String date){
        String dbDay = date.split("T")[0].split("-")[2];
        String dbYear = date.split("T")[0].split("-")[0];
        String dbMonth = date.split("T")[0].split("-")[1];
        String time = date.split("T")[1].split(":")[0] + ":"
                + date.split("T")[1].split(":")[1];
        String today = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String yesterday = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1);
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        return dbDay.equals(today) && dbMonth.equals(month) && dbYear.equals(year) ?  "Today at " + time
                : dbDay.equals(yesterday) && dbMonth.equals(month) && dbYear.equals(year) ? "Yesterday at " + time
                : setAnyOtherDay(dbMonth, dbDay);

    }
    private String setAnyOtherDay(String dbMonth, String dbDay){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.parseInt(dbMonth) - 1);
        return String.format(Locale.US,"%tB", cal) + " " + dbDay;

    }


}
