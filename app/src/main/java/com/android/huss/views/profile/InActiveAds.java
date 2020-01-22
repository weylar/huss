package com.android.huss.views.profile;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.huss.R;
import com.android.huss.models.AllAds;

import kotlin.jvm.JvmOverloads;

public class InActiveAds extends Fragment {
    private AllAds ad;

    @JvmOverloads
    InActiveAds(AllAds ad) {
        this.ad = ad;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_ads, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.activeAds);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new InactiveAdsAdapter(getContext(), ad.getData()));

        return view;
    }



}
