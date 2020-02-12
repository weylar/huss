package com.huss.android.views.profile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huss.android.R;
import com.huss.android.models.AllAds;

import java.util.List;

import kotlin.jvm.JvmOverloads;

public class ActiveAdsFragment extends Fragment {
    private List<AllAds.Data> active;

    @JvmOverloads
    ActiveAdsFragment(List<AllAds.Data> active) {
        this.active = active;
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
        if (active.size() < 1){
            view.findViewById(R.id.animation_view).setVisibility(View.VISIBLE);

        }
        recyclerView.setAdapter(new ActiveAdsAdapter(getContext(), active));



        return view;
    }



}