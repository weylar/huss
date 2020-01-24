package com.android.huss.views.profile;

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

import com.android.huss.R;
import com.android.huss.models.Ad;
import com.android.huss.models.AdImage;
import com.android.huss.models.Ads;
import com.android.huss.models.AllAds;
import com.android.huss.models.Image;
import com.android.huss.models.SingleAd;
import com.android.huss.viewModels.UserAdsViewModel;
import com.android.huss.views.ads.createAds.CreateAds;
import com.android.huss.views.ads.singleAds.SingleAds;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import timber.log.Timber;

import static com.android.huss.utility.Utility.MY_PREFERENCES;
import static com.android.huss.utility.Utility.TOKEN;
import static com.android.huss.views.ads.singleAds.SingleAds.ID;
import static com.android.huss.views.ads.singleAds.SingleAds.NAME;

public class InactiveAdsAdapter extends RecyclerView.Adapter<InactiveAdsAdapter.CustomViewHolder>{


        private List<AllAds.Data> dataList;
        private Context context;

         InactiveAdsAdapter(Context context, List<AllAds.Data> dataList){
            this.context = context;
            this.dataList = dataList;

        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            TextView txtTitle;
            TextView price;
            TextView description,negotiable;
            SparkButton favorite;
            private ImageView image, moreIcon;

            CustomViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                txtTitle = mView.findViewById(R.id.title);
                image = mView.findViewById(R.id.image);
                price = mView.findViewById(R.id.price);
                favorite = mView.findViewById(R.id.favorite);
                description = mView.findViewById(R.id.description);
                negotiable = mView.findViewById(R.id.negotiable);
                moreIcon = mView.findViewById(R.id.more_icon);
            }
        }




        @NotNull
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.my_ads_layout, parent, false);
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
            AllAds.Data ad = dataList.get(position);
            if (ad.getStatus().equals(context.getString(R.string.inactive))) {
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
                    popup.getMenuInflater().inflate(R.menu.my_ad_menu_inactive, popup.getMenu());
                    popup.setOnMenuItemClickListener(item -> {
                        if (item.getItemId() ==R.id.renew_ad){
                            AlertDialog.Builder info = new AlertDialog.Builder(context);
                            info.setMessage("Your Ad has successfully been activated for the next  7 days, click Ok to view Ad");
                            info.setTitle("Activated!");
                            info.setNeutralButton("Ok", (dialog, id) -> {
                               dialog.dismiss();
                               context.startActivity(new Intent(context, SingleAds.class).putExtra(ID, ad.getId()));

                                });


                            info.show();
                        }else if (item.getItemId() == R.id.edit_ad) {
                            Intent intent = new Intent(context, CreateAds.class);
                            intent.putExtra(ID, ad.getId());
                            context.startActivity(intent);

                        } else if (item.getItemId() == R.id.delete_ad) {
                            AlertDialog.Builder info = new AlertDialog.Builder(context);
                            info.setMessage("Are you sure you want to permanently delete this Ad?");
                            info.setTitle("Confirm delete!");

                            info.setPositiveButton("Yes delete", (dialog, id) -> {
                                UserAdsViewModel userAdsViewModel = ViewModelProviders.of((FragmentActivity) context).get(UserAdsViewModel.class);
                                userAdsViewModel.initDelete(context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE).getString(TOKEN, ""),
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
       }

        @Override
        public int getItemCount() {
            if (dataList != null) {
                return dataList.size();
            }
            return 0;
        }
    }

