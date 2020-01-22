package com.android.huss.views.home;

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
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;

public class LatestAdsAdapter extends RecyclerView.Adapter<LatestAdsAdapter.CustomViewHolder>{


        private List<Ads> dataList;
        private Context context;

        public LatestAdsAdapter(Context context, List<Ads> dataList){
            this.context = context;
            this.dataList = dataList;

        }

//        class CustomViewHolder extends RecyclerView.ViewHolder {
//
//            public final View mView;
//
//            TextView txtTitle;
//            TextView price;
//            TextView description;
//            ImageView favorite;
//            private ImageView image;
//
//            CustomViewHolder(View itemView) {
//                super(itemView);
//                mView = itemView;
//                txtTitle = mView.findViewById(R.id.title);
//                image = mView.findViewById(R.id.image);
//                price = mView.findViewById(R.id.price);
//                favorite = mView.findViewById(R.id.favorite);
//                description = mView.findViewById(R.id.description);
//            }
//        }

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

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.top_ads_view, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
         //   String adTitle = dataList.get(position).getTitle();
            holder.label.setVisibility(View.GONE);
            holder.txtTitle.setText( "iPhone X Max"/*adTitle.length() > 70 ? adTitle.substring(0, 67).concat("...") : adTitle*/);
            holder.price.setText("$35"/*dataList.get(position).getPrice()*/);
//            holder.description.setText("Lorem ipsum dolor sit amet, minim veniam, ut aliquip ex ea commodo consequat");
          //  if (dataList.get(position).getFavorite().equals("Yes")){
            holder.favorite.setEventListener(new SparkEventListener(){

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

          //  }else{
                //holder.favorite.setImageResource(R.drawable.favorite_no);
           // }



//            Picasso.Builder builder = new Picasso.Builder(context);
//            builder.build().load(dataList.get(position).getFeatureImgUrl())
//                    .into(holder.image);

        }

        @Override
        public int getItemCount() {
            if (dataList != null) {
                return dataList.size();
            }
            return 0;
        }
    }


