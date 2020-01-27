package com.huss.android.views.profile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.huss.android.models.AllAds;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ProfileAdsPager extends FragmentPagerAdapter {
    private List<AllAds.Data> active;
    private List<AllAds.Data> inactive;

    ProfileAdsPager(FragmentManager fragmentManager, List<AllAds.Data> active, List<AllAds.Data> inactive) {
        super(fragmentManager);
        this.active = active;
        this.inactive = inactive;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new InActiveAdsFragment(inactive);
        }
        return new ActiveAdsFragment(active);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 1) {
            return "Inactive Ads";
        }
        return "Active Ads";
    }

}

