package com.android.huss.views.profile;

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
import com.android.huss.views.ads.singleAds.SingleAds;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.android.huss.views.ads.singleAds.SingleAds.ID;
import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class ActiveAdsAdapter extends RecyclerView.Adapter<ActiveAdsAdapter.CustomViewHolder>{


        private List<Ads> dataList;
        private Context context;

        public ActiveAdsAdapter(Context context, List<Ads> dataList){
            this.context = context;
            this.dataList = dataList;

        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            TextView txtTitle;
            TextView price;
            TextView description;
            ImageView favorite;
            private ImageView image;

            CustomViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                txtTitle = mView.findViewById(R.id.title);
                image = mView.findViewById(R.id.image);
                price = mView.findViewById(R.id.price);
                favorite = mView.findViewById(R.id.favorite);
                description = mView.findViewById(R.id.description);
            }
        }




        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.latest_ads_view, parent, false);
            view.setOnClickListener(v -> {
                String id = String.valueOf(v.getId());
                Intent intent =  new Intent(context, SingleAds.class);
                intent.putExtra(ID, id);
                intent.putExtra(NAME, String.valueOf(v.getTag()));
                context.startActivity(intent);
            });
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            Ads ad = dataList.get(position);
//            if (ad.getStatus().equals(context.getString(R.string.active))) {
                String adTitle = ad.getTitle();
                holder.txtTitle.setText("iPhone X Max"/*adTitle.length() > 70 ? adTitle.substring(0, 67).concat("...") : adTitle*/);
                holder.price.setText("$35"/*ad.getPrice()*/);
                String description = "Lorem ipsum dolor sit amet, minim veniam, ut aliquip ex ea commodo consequat";
                holder.description.setText(description/*description.length() > 90 ? description.substring(0, 86).concat("..."): description*/);
                //  if (ad.getFavorite().equals("Yes")){
                holder.favorite.setImageResource(R.drawable.favorite_yes);
                holder.itemView.setId(ad.getId());
                holder.itemView.setTag(adTitle);
                holder.favorite.setVisibility(View.GONE);


                //  }else{
                //holder.favorite.setImageResource(R.drawable.favorite_no);
                // }


                Picasso.Builder builder = new Picasso.Builder(context);
                builder.downloader(new OkHttp3Downloader(context));
                builder.build().load(dataList.get(position).getFeatureImgUrl())
                        .placeholder((R.drawable.ic_launcher_background))
                        .error(R.drawable.ic_launcher_background)
                        .into(holder.image);

            }
//        }

        @Override
        public int getItemCount() {
            if (dataList != null) {
                return dataList.size();
            }
            return 0;
        }
    }


