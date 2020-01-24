package com.android.huss.views.ads.latestAds;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.huss.R;
import com.android.huss.models.AllAds;
import com.android.huss.views.ads.singleAds.SingleAds;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.android.huss.views.ads.singleAds.SingleAds.ID;
import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class AllLatestAdsAdapter extends RecyclerView.Adapter<AllLatestAdsAdapter.CustomViewHolder> {


    private List<AllAds.Data> dataList;
    private Context context;

    AllLatestAdsAdapter(Context context, List<AllAds.Data> dataList) {
        this.context = context;
        this.dataList = dataList;

    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.latest_ads_view, parent, false);
        view.setOnClickListener(v -> {
            String id = String.valueOf(v.getId());
            Intent intent = new Intent(context, SingleAds.class);
            intent.putExtra(ID, id);
            intent.putExtra(NAME, String.valueOf(v.getTag()));
            context.startActivity(intent);
        });
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        AllAds.Data ads = dataList.get(position);
        holder.txtTitle.setText(ads.getTitle());
        holder.price.setText(String.format("₦%s", String.format("%,d", Integer.parseInt(ads.getPrice()))));
        holder.description.setText(ads.getDescription());
        holder.itemView.setId(ads.getId());
        holder.itemView.setTag(ads.getTitle());
        holder.favorite.setChecked(ads.getFavorites());
        if (ads.getIsNegotiable()){
            holder.negotiable.setText(context.getResources().getString(R.string.negotiable));
        }else{
            holder.negotiable.setText(context.getResources().getString(R.string.fixed));
        }

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

        for (AllAds.Data.Image image: ads.getImages()){
            if (image.getDisplayImage()){
                Picasso.Builder builder = new Picasso.Builder(context);
                builder.build().load(image.getImageUrl())
                        .into(holder.image);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        TextView txtTitle;
        TextView price, negotiable;
        TextView description;
        SparkButton favorite;
        ImageView image;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.title);
            price = mView.findViewById(R.id.price);
            favorite = mView.findViewById(R.id.favorite);
            description = mView.findViewById(R.id.description);
            image = mView.findViewById(R.id.image);
            negotiable = mView.findViewById(R.id.negotiable);
        }
    }
}


