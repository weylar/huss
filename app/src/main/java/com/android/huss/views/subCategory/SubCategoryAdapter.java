package com.android.huss.views.subCategory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.models.SubCategory;
import com.android.huss.views.latestAds.AllLatestAdsAdapter;
import com.android.huss.views.latestAds.LatestAds;
import com.android.huss.views.singleAds.SingleAds;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.android.huss.views.singleAds.SingleAds.ID;
import static com.android.huss.views.singleAds.SingleAds.NAME;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.CustomViewHolder>{


    private List<SubCategory> dataList;
    private Context context;

     SubCategoryAdapter(Context context, List<SubCategory> dataList){
        this.context = context;
        this.dataList = dataList;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        CustomViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sub_cat_view, parent, false);
        view.setOnClickListener(v -> {
            String name = String.valueOf(v.getTag());
            Intent intent =  new Intent(context, LatestAds.class);
            intent.putExtra(NAME, name);
            context.startActivity(intent);
        });
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        String adTitle = dataList.get(position).getName();
        holder.txtTitle.setText( "iPhone X Max"/*adTitle.length() > 70 ? adTitle.substring(0, 67).concat("...") : adTitle*/);
        holder.itemView.setTag(adTitle);


    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }
}



