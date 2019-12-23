package com.android.huss.views.profile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public  class ProfileAdsPager extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        ProfileAdsPager(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return InActiveAds.newInstance();
            }
            return ActiveAds.newInstance();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1) {
                return "Inactive Ads";
            }
            return "Active Ads";
        }

    }

