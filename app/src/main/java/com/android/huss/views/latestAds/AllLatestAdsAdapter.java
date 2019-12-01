package com.android.huss.views.latestAds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllLatestAdsAdapter extends RecyclerView.Adapter<AllLatestAdsAdapter.CustomViewHolder>{


        private List<Ads> dataList;
        private Context context;

        public AllLatestAdsAdapter(Context context, List<Ads> dataList){
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
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            String adTitle = dataList.get(position).getTitle();
            holder.txtTitle.setText( "iPhone X Max"/*adTitle.length() > 70 ? adTitle.substring(0, 67).concat("...") : adTitle*/);
            holder.price.setText("$35"/*dataList.get(position).getPrice()*/);
            holder.description.setText("Lorem ipsum dolor sit amet, minim veniam, ut aliquip ex ea commodo consequat");
          //  if (dataList.get(position).getFavorite().equals("Yes")){
               holder.favorite.setImageResource(R.drawable.favorite_yes);

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

        @Override
        public int getItemCount() {
            if (dataList != null) {
                return dataList.size();
            }
            return 0;
        }
    }


