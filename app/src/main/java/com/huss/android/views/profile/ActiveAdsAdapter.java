package com.huss.android.views.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.huss.android.R;
import com.huss.android.models.AllAds;
import com.huss.android.viewModels.UserAdsViewModel;
import com.huss.android.views.ads.createAds.CreateAds;
import com.huss.android.views.ads.singleAds.SingleAds;
import com.huss.android.utility.Utility;
import com.huss.android.views.ads.createAds.CreateAds;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import timber.log.Timber;

public class ActiveAdsAdapter extends RecyclerView.Adapter<ActiveAdsAdapter.CustomViewHolder> {


    private List<AllAds.Data> dataList;
    private Context context;

    ActiveAdsAdapter(Context context, List<AllAds.Data> dataList) {
        this.context = context;
        this.dataList = dataList;

    }


    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_ads_layout, parent, false);
        view.setOnClickListener(v -> {
            int id = v.getId();
            Intent intent = new Intent(context, SingleAds.class);
            intent.putExtra(SingleAds.ID, id);
            intent.putExtra(SingleAds.NAME, String.valueOf(v.getTag()));
            context.startActivity(intent);
        });
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull CustomViewHolder holder, int position) {
        AllAds.Data ad = dataList.get(position);
            String adTitle = ad.getTitle();
            holder.txtTitle.setText(adTitle);
            holder.price.setText(String.format("₦%s", String.format("%,d", Integer.parseInt(ad.getPrice()))));
            holder.description.setText(ad.getDescription());
            if (ad.getIsNegotiable()) {
                holder.negotiable.setText(R.string.negotiable);
            } else holder.negotiable.setText(R.string.fixed);
            holder.itemView.setId(ad.getId());
            holder.itemView.setTag(adTitle);


            holder.moreIcon.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.my_ad_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                   if (item.getItemId() == R.id.edit_ad) {
                       Intent intent = new Intent(context, CreateAds.class);
                       intent.putExtra(SingleAds.ID, ad.getId());
                       context.startActivity(intent);

                    } else if (item.getItemId() == R.id.delete_ad) {
                        AlertDialog.Builder info = new AlertDialog.Builder(context);
                        info.setMessage("Are you sure you want to permanently delete this Ad?");
                        info.setTitle("Confirm delete!");

                        info.setPositiveButton("Yes delete", (dialog, id) -> {
                            UserAdsViewModel userAdsViewModel = ViewModelProviders.of((FragmentActivity) context).get(UserAdsViewModel.class);
                            userAdsViewModel.initDelete(context.getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(Utility.TOKEN, ""),
                                    ad.getId());
                            userAdsViewModel.deleteUserAd().observe((LifecycleOwner) context, ads -> {
                                Timber.e("Deleted successfully");
                                dataList.remove(position);
                                notifyItemRemoved(position);

                            });
                        });

                        info.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss());
                        info.setIcon(R.drawable.ic_warning_red);
                        info.show();


                    }

                    return true;
                });
                popup.show();
            });

            if (ad.getImages() != null) {
                for (AllAds.Data.Image img : ad.getImages()) {
                    if (img.getDisplayImage()) {
                        Picasso.Builder builder = new Picasso.Builder(context);
                        builder.build().load(img.getImageUrl())
                                .into(holder.image);
                        break;
                    }
                }
            }



    }


    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        TextView description, negotiable, price, txtTitle;
        ImageView moreIcon;
        private ImageView image;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.title);
            image = mView.findViewById(R.id.image);
            price = mView.findViewById(R.id.price);
            description = mView.findViewById(R.id.description);
            moreIcon = mView.findViewById(R.id.more_icon);
            negotiable = mView.findViewById(R.id.negotiable);

        }
    }
}


