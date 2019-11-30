package com.android.huss.views.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.huss.R;
import com.android.huss.models.Ads;
import com.android.huss.models.Category;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopAdsRecycler extends RecyclerView.Adapter<TopAdsRecycler.CustomViewHolder>{


        private List<Ads> dataList;
        private Context context;

        public TopAdsRecycler(Context context, List<Ads> dataList){
            this.context = context;
            this.dataList = dataList;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            TextView txtTitle;
            TextView price;
            TextView favorite;
            private ImageView image;

            CustomViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                txtTitle = mView.findViewById(R.id.title);
                image = mView.findViewById(R.id.image);
                price = mView.findViewById(R.id.price);
                favorite = mView.findViewById(R.id.favorite);
            }
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.cat_view, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            holder.txtTitle.setText(dataList.get(position).getName());
            holder.price.setText(dataList.get(position).getPrice());
            if (dataList.get(position).isFavorite()){
               // holder.favorite.setText(dataList.get(position).getName());
            }else{
               //TODO: Do somethng
            }



            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(dataList.get(position).getFeatureImageUrl())
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.image);

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }


