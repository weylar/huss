package com.huss.android.views.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.huss.android.R;
import com.huss.android.models.AllAds;
import com.huss.android.views.ads.singleAds.SingleAdsActivity;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.jetbrains.annotations.NotNull;

import static com.huss.android.utility.Utility.STANDARD_AD;
import static com.huss.android.views.ads.singleAds.SingleAdsActivity.ID;
import static com.huss.android.views.ads.singleAds.SingleAdsActivity.NAME;
import static com.huss.android.views.home.MainActivity.checkLoggedIn;

public class LatestAdsAdapter extends RecyclerView.Adapter<LatestAdsAdapter.CustomViewHolder> {


    private AllAds dataList;
    private Context context;

    LatestAdsAdapter(Context context, AllAds dataList) {
        this.context = context;
        this.dataList = dataList;

    }


    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.top_ads_view, parent, false);
        view.setOnClickListener(v -> {
            String id = String.valueOf(v.getId());
            Intent intent = new Intent(context, SingleAdsActivity.class);
            intent.putExtra(ID, id);
            intent.putExtra(NAME, String.valueOf(v.getTag()));
            context.startActivity(intent);
        });
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull CustomViewHolder holder, int position) {
        AllAds.Data ad = dataList.getData().get(position);
        if (ad.getType().equals(STANDARD_AD)) {
            holder.label.setVisibility(View.GONE);
            holder.txtTitle.setText(ad.getTitle());
            holder.price.setText(String.format("₦%s", String.format("%,d", Integer.parseInt(ad.getPrice()))));
            holder.itemView.setId(ad.getId());
            if (checkLoggedIn(context)){
                holder.favorite.setChecked(ad.getFavorites());
                holder.favorite.setEventListener(new SparkEventListener() {

                    @Override
                    public void onEvent(ImageView button, boolean buttonState) {
                        if (buttonState) {
                            // Button is active
                        } else {
                            // Button is inactive
                        }
                    }

                    @Override
                    public void onEventAnimationEnd(ImageView button, boolean buttonState) {

                    }

                    @Override
                    public void onEventAnimationStart(ImageView button, boolean buttonState) {

                    }
                });
            }else{
                holder.favorite.setVisibility(View.GONE);
            }
            for (AllAds.Data.Image image : ad.getImages()) {
                if (image.getDisplayImage()) {
                    Picasso.Builder builder = new Picasso.Builder(context);
                    builder.build().load(image.getImageUrl())
                            .into(holder.image);
                }
            }


        }
    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.getData().size();
        }
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView txtTitle;
        TextView price;
        TextView label, negotiable;
        SparkButton favorite;
        private ImageView image;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.title);
            image = mView.findViewById(R.id.image);
            price = mView.findViewById(R.id.price);
            favorite = mView.findViewById(R.id.favorite);
            label = mView.findViewById(R.id.label);
        }
    }
}


