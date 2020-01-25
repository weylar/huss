package com.huss.android.views.ads.singleAds;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.huss.android.R;
import com.huss.android.models.SingleAd;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import timber.log.Timber;

public class SimilarAdsAdapter extends RecyclerView.Adapter<SimilarAdsAdapter.CustomViewHolder> {


    private List<SingleAd.SimilarAd> dataList;
    private Context context;

    SimilarAdsAdapter(Context context, List<SingleAd.SimilarAd> dataList) {
        this.context = context;
        this.dataList = dataList;
        Timber.e(dataList.size() +"");

    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.top_ads_view, parent, false);
        view.setOnClickListener(v -> {
            String id = String.valueOf(v.getId());
            Intent intent = new Intent(context, SingleAds.class);
            intent.putExtra(SingleAds.ID, id);
            context.startActivity(intent);
        });

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        SingleAd.SimilarAd ad = dataList.get(position);
        holder.label.setVisibility(View.GONE);
        holder.txtTitle.setText(ad.getTitle());
        holder.price.setText(String.format("₦%s", String.format("%,d", Integer.parseInt(ad.getPrice()))));
//                holder.favorite.setChecked(ad.is);
        holder.itemView.setId(ad.getId());
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
        for (SingleAd.Image image : ad.getImages()) {
            if (image.getDisplayImage()) {
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
        TextView price;
        TextView label;
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


