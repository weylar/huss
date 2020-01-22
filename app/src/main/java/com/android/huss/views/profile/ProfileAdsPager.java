package com.android.huss.views.profile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.android.huss.models.AllAds;
import org.jetbrains.annotations.NotNull;


public class ProfileAdsPager extends FragmentPagerAdapter {
    private AllAds ad;

    ProfileAdsPager(FragmentManager fragmentManager, AllAds ad) {
        super(fragmentManager);
        this.ad = ad;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new InActiveAds(ad);
        }
        return new ActiveAds(ad);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 1) {
            return "Inactive Ads";
        }
        return "Active Ads";
    }

}

