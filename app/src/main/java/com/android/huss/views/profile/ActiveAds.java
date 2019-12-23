package com.android.huss.views.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.viewModels.AdsViewModel;
import com.android.huss.viewModels.UserAdsViewModel;
import com.android.huss.views.latestAds.AllLatestAdsAdapter;
import com.android.huss.views.latestAds.LatestAds;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.List;

public class ActiveAds extends Fragment {

    public ActiveAds() {
    }


    static ActiveAds newInstance() {
        return new ActiveAds();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_ads, container, false);
        LVCircularZoom progressBarLatestAds = view.findViewById(R.id.progress);
        progressBarLatestAds.setViewColor(getResources().getColor(R.color.gray));
        progressBarLatestAds.startAnim(100);
        RecyclerView recyclerView = view.findViewById(R.id.activeAds);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        UserAdsViewModel userAdsViewModel = ViewModelProviders.of(this).get(UserAdsViewModel.class);
        userAdsViewModel.init("2");
        userAdsViewModel.getUserAds().observe(this, ads -> {
            recyclerView.setAdapter(new ActiveAdsAdapter(getContext(), ads));
            progressBarLatestAds.stopAnim();
            progressBarLatestAds.setVisibility(View.GONE);
            progressBarLatestAds.stopAnim();

        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
