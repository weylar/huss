package com.android.huss.views.Home;

import android.content.Context;
import android.util.Log;
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

public class TopAdsAdapter extends RecyclerView.Adapter<TopAdsAdapter.CustomViewHolder>{


        private List<Ads> dataList;
        private Context context;

        public TopAdsAdapter(Context context, List<Ads> dataList){
            this.context = context;
            this.dataList = dataList;

        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            TextView txtTitle;
            TextView price;
            TextView label;
            ImageView favorite;
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

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.top_ads_view, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            String adTitle = dataList.get(position).getTitle();
            holder.txtTitle.setText( adTitle.length() > 14 ? adTitle.substring(0, 14).concat("...") : adTitle);
            holder.price.setText("$35"/*dataList.get(position).getPrice()*/);
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


