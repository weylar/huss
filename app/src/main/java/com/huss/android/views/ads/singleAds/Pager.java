package com.huss.android.views.ads.singleAds;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.huss.android.R;
import com.huss.android.views.adsImages.ImageFull;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.huss.android.views.adsImages.ImageFull.PAGE;

public class Pager extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> allImagesUrl;

    Pager(Context context, ArrayList<String> allImagesUrl) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.allImagesUrl = allImagesUrl;


    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup collection, int position) {
        String url = allImagesUrl.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.ads_image_view, collection, false);
        ImageView photoView = itemView.findViewById(R.id.image_product);
        photoView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ImageFull.class);
            intent.putExtra(PAGE, position);
            intent.putStringArrayListExtra("URL", allImagesUrl);
            mContext.startActivity(intent);
        });

        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.build().load(url)
                .into(photoView);
        collection.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, @NotNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return allImagesUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }



}
