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
import com.android.huss.models.Ads;
import com.android.huss.views.ads.singleAds.SingleAds;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.android.huss.views.ads.singleAds.SingleAds.ID;
import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class AllLatestAdsAdapter extends RecyclerView.Adapter<AllLatestAdsAdapter.CustomViewHolder>{


        private List<Ads> dataList;
        private Context context;

        AllLatestAdsAdapter(Context context, List<Ads> dataList){
            this.context = context;
            this.dataList = dataList;

        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            TextView txtTitle;
            TextView price;
            TextView description;
            SparkButton favorite;
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




        @NotNull
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
//            String adTitle = dataList.get(position).getTitle();
            holder.txtTitle.setText( "iPhone X Max"/*adTitle.length() > 70 ? adTitle.substring(0, 67).concat("...") : adTitle*/);
            holder.price.setText("$35"/*dataList.get(position).getPrice()*/);
            String description = "Lorem ipsum dolor sit amet, minim veniam, ut aliquip ex ea commodo consequat";
            holder.description.setText( description/*description.length() > 90 ? description.substring(0, 86).concat("..."): description*/);
          //  if (dataList.get(position).getFavorite().equals("Yes")){

//               holder.itemView.setId(dataList.get(position).getId());
//               holder.itemView.setTag(adTitle);
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


//
//            Picasso.Builder builder = new Picasso.Builder(context);
//            builder.build().load(dataList.get(position).getFeatureImgUrl())
//                    .into(holder.image);

        }

        @Override
        public int getItemCount() {
            if (dataList != null) {
                return 20; /*dataList.size();*/
            }
            return 0;
        }
    }


