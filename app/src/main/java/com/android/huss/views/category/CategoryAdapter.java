package com.android.huss.views.category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.huss.R;
import com.android.huss.models.Category;
import com.android.huss.views.subCategory.SubCategoryView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>{


        private List<Category.Data> dataList;
        private Context context;

        public CategoryAdapter(Context context, List<Category.Data> dataList){
            this.context = context;
            this.dataList = dataList;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            TextView txtTitle;
            private ImageView icon;
            CardView cardView;

            CustomViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                txtTitle = mView.findViewById(R.id.title);
                icon = mView.findViewById(R.id.icon);
                cardView = mView.findViewById(R.id.card_category);
            }
        }

        @NotNull
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
        public void onBindViewHolder(@NotNull CustomViewHolder holder, int position) {
            if (position == 0){
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            }else{
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }

            holder.txtTitle.setText(dataList.get(position).getName().split(" ")[0].replace(",", ""));
            holder.mView.setTag(dataList.get(position).getName());

            Picasso.Builder builder = new Picasso.Builder(context);
            builder.build().load(dataList.get(position).getIconUrl())
                    .into(holder.icon);

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }


