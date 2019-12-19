package com.android.huss.views.adsImages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.android.huss.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.android.huss.views.adsImages.ImageFull.PAGE;

public class FullAdsPager extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<String> allImagesUrl;

    public FullAdsPager(Context context, ArrayList<String> allImagesUrl) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.allImagesUrl = allImagesUrl;

    }

    @Override
    public int getCount() {
        return (null != allImagesUrl ? allImagesUrl.size() : 0);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.ads_image_view, container, false);
        PhotoView photoView = itemView.findViewById(R.id.image);
        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.build().load(allImagesUrl.get(position))
                .placeholder((R.drawable.sample))
                .error(R.drawable.flag)
                .into(photoView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
