package com.android.huss.views.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.ProfileViewModel;
import com.android.huss.viewModels.UserAdsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.huss.views.ads.singleAds.SingleAds.formatViews;

public class Profile extends AppCompatActivity {
    ViewPager vpPager;
    TabLayout tabLayout;
    ProfileAdsPager profileAdsPager;
    TextView totalAdsCount, totalActiveAdsCount, totalInActiveAdsCount, name, phone, email;
    CircleImageView profileImage;
    LVCircularZoom lvCircularZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        vpPager = findViewById(R.id.viewPagerProfile);
        tabLayout = findViewById(R.id.tabLayoutProfile);
        totalActiveAdsCount = findViewById(R.id.activeAdsCount);
        totalAdsCount = findViewById(R.id.totalAdsCount);
        totalInActiveAdsCount = findViewById(R.id.inActiveAdsCount);
        profileImage = findViewById(R.id.profile_img);
        name = findViewById(R.id.profileName);
        lvCircularZoom = findViewById(R.id.progress2);
        lvCircularZoom.setViewColor(getResources().getColor(R.color.gray));
        lvCircularZoom.startAnim(100);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        profileAdsPager = new ProfileAdsPager(getSupportFragmentManager());
        vpPager.setAdapter(profileAdsPager);
        tabLayout.setupWithViewPager(vpPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAdsDetails();
        getProfileDetails();

    }

    private void getAdsDetails() {
        UserAdsViewModel userAdsViewModel = ViewModelProviders.of(this).get(UserAdsViewModel.class);
        userAdsViewModel.init("2");
        userAdsViewModel.getUserAds().observe(this, ads -> {
            ArrayList<Ads> re = new ArrayList();
            Ads ade = new Ads();
            ade.setStatus("Active");
            ade.setStatus("Inactive");
            re.add(ade);
            int activeCount = 0;
            int inactiveCount = 0;
            for (Ads ad: re){
                if (ad.getStatus().equals(getString(R.string.active))) activeCount++;
                else inactiveCount++;
            }
            totalAdsCount.setText( formatViews(ads.size() + ""));
            totalActiveAdsCount.setText(formatViews(activeCount + "" ));
            totalInActiveAdsCount.setText( formatViews(inactiveCount + ""));


        });
    }

    @SuppressLint("SetTextI18n")
    private void getProfileDetails(){
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init("2");
        profileViewModel.getProfile().observe(this, profile -> {

            profile.getData().setFirstName("Aminu");
            profile.getData().setLastName("Idris");
            profile.getData().setPhoneNumber("08138028915");
            profile.getData().setEmail("idrisaminu861@gmail.com");
            name.setText(profile.getData().getFirstName() + " " + profile.getData().getLastName());
            phone.setText(profile.getData().getPhoneNumber());
            email.setText(profile.getData().getEmail());

            Picasso.Builder builder = new Picasso.Builder(this);
            builder.build().load(profile.getData().getProfileImgUrl())
                    .placeholder((R.drawable.sample))
                    .error(R.drawable.sample)
                    .into(profileImage);

            lvCircularZoom.stopAnim();
            lvCircularZoom.setVisibility(View.GONE);
            lvCircularZoom.stopAnim();

        });
    }

    public void goBack(View view) {
        finish();
    }

    public void openSetting(View view) {
        /*TODO: Open settings*/
    }


}
