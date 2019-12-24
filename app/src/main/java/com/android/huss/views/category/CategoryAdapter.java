package com.android.huss.views.category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.huss.R;
import com.android.huss.models.Category;
import com.android.huss.views.subCategory.SubCategoryView;

import java.util.List;

import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>{


        private List<Category> dataList;
        private Context context;

        public CategoryAdapter(Context context, List<Category> dataList){
            this.context = context;
            this.dataList = dataList;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            TextView txtTitle;
            private ImageView icon;

            CustomViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                txtTitle = mView.findViewById(R.id.title);
                icon = mView.findViewById(R.id.icon);
            }
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.cat_view, parent, false);
            view.setOnClickListener(v -> {
                String name = String.valueOf(v.getTag());
                Intent intent =  new Intent(context, SubCategoryView.class);
                intent.putExtra(NAME, name);
                context.startActivity(intent);
            });
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            holder.txtTitle.setText(dataList.get(position).getName());
            holder.mView.setTag(dataList.get(position).getName());

//            Picasso.Builder builder = new Picasso.Builder(context);
//            builder.downloader(new OkHttp3Downloader(context));
//            builder.build().load(dataList.get(position).getIconUrl())
//                    .placeholder((R.drawable.ic_launcher_background))
//                    .error(R.drawable.ic_launcher_background)
//                    .into(holder.icon);

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }


