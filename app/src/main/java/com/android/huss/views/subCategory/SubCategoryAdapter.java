package com.android.huss.views.subCategory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.huss.R;
import com.android.huss.models.SubCategory;
import com.android.huss.views.ads.latestAds.LatestAds;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.CustomViewHolder>{


    public static final String CATEGORY = "category";
    private List<SubCategory.Data> dataList;
    private Context context;
    private String category;

     SubCategoryAdapter(Context context, List<SubCategory.Data> dataList, String category){
        this.context = context;
        this.dataList = dataList;
        this.category = category;

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        CustomViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);

        }
    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sub_cat_view, parent, false);
        view.setOnClickListener(v -> {
            String name = String.valueOf(v.getTag());
            Intent intent =  new Intent(context, LatestAds.class);
            intent.putExtra(NAME, name);
            intent.putExtra(CATEGORY, category);
            context.startActivity(intent);
        });
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        String adTitle = dataList.get(position).getName();
        holder.txtTitle.setText( adTitle);
        holder.itemView.setTag(adTitle);


    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }
}



