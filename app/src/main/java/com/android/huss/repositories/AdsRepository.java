package com.android.huss.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.huss.data.HussAPI;
import com.android.huss.data.RetrofitClientInstance;
import com.android.huss.models.Ads;
import com.android.huss.models.AllAds;
import com.android.huss.models.SingleAd;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.android.huss.utility.Utility.BEARER;

public class AdsRepository {

    private static AdsRepository adsRepository;

    public static AdsRepository getInstance() {
        if (adsRepository == null) {
            adsRepository = new AdsRepository();
        }
        return adsRepository;
    }

    public MutableLiveData<List<Ads>> getAds() {
        final MutableLiveData<List<Ads>> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<Ads>> call = retrofit.getAds();
        call.enqueue(new Callback<List<Ads>>() {
            @Override
            public void onResponse(@NotNull Call<List<Ads>> call, @NotNull Response<List<Ads>> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponse: SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Ads>> call, Throwable t) {
                Timber.e("%sFailed", t.getMessage());
            }
        });
        return adsData;
    }

    public MutableLiveData<List<Ads>> getSearchAds(String key) {
        final MutableLiveData<List<Ads>> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<Ads>> call = retrofit.getSearchAds(key);
        call.enqueue(new Callback<List<Ads>>() {
            @Override
            public void onResponse(@NotNull Call<List<Ads>> call, @NotNull Response<List<Ads>> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponse: SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Ads>> call, @NotNull Throwable t) {
                Timber.e("%sFailed", t.getMessage());
            }
        });
        return adsData;
    }

    public MutableLiveData<AllAds> getUserAds(String token) {
        final MutableLiveData<AllAds> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<AllAds> call = retrofit.getUserAds(BEARER + " " + token);
        call.enqueue(new Callback<AllAds>() {
            @Override
            public void onResponse(@NotNull Call<AllAds> call, @NotNull Response<AllAds> response) {
                if (response.isSuccessful()) {
                    Timber.e("Success");
                    adsData.setValue(response.body());

                }
            }

            @Override
            public void onFailure(@NotNull Call<AllAds> call, @NotNull Throwable t) {
                Timber.e("%sFailed", t.getMessage());

            }
        });
        return adsData;
    }

    public MutableLiveData<List<Ads>> getFavoriteAds(String userId) {
        final MutableLiveData<List<Ads>> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<Ads>> call = retrofit.getFavoriteAds(userId);
        call.enqueue(new Callback<List<Ads>>() {
            @Override
            public void onResponse(Call<List<Ads>> call, Response<List<Ads>> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponseFav: SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Ads>> call, Throwable t) {
                Timber.e("%sFav Failed", t.getMessage());
            }
        });
        return adsData;
    }

    public MutableLiveData<SingleAd> getSingleAds(String token, String id) {
        final MutableLiveData<SingleAd> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<SingleAd> call = retrofit.getSingleAds(BEARER + " " + token, id);
        call.enqueue(new Callback<SingleAd>() {
            @Override
            public void onResponse(@NotNull Call<SingleAd> call, @NotNull Response<SingleAd> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponse: SINGLE SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SingleAd> call, @NotNull Throwable t) {
                Timber.e("%sSingle Ads Failed", t.getMessage());
            }
        });
        return adsData;
    }

    public MutableLiveData<SingleAd> updateAd(String token, SingleAd.Data ad) {
        final MutableLiveData<SingleAd> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<SingleAd> call = retrofit.updateAd(BEARER + " " + token, ad.getId().toString(),
                ad.getCategoryName(), ad.getSubCategoryName(),
                ad.getTitle(), ad.getDescription(), ad.getPrice(),
                ad.getLocation(), ad.getIsNegotiable());
        call.enqueue(new Callback<SingleAd>() {
            @Override
            public void onResponse(@NotNull Call<SingleAd> call, @NotNull Response<SingleAd> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponse: SINGLE SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SingleAd> call, @NotNull Throwable t) {
                Timber.e("%sSingle Ads Failed", t.getMessage());
            }
        });
        return adsData;
    }

    public MutableLiveData<Ads> deleteUserAd(String token, int id) {
        final MutableLiveData<Ads> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Ads> call = retrofit.deleteUserAd(BEARER + " " + token, id);
        call.enqueue(new Callback<Ads>() {
            @Override
            public void onResponse(@NotNull Call<Ads> call, @NotNull Response<Ads> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponse: SINGLE SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Ads> call, @NotNull Throwable t) {
                Timber.e("%sSingle Ads Failed", t.getMessage());
            }
        });
        return adsData;
    }

    public MutableLiveData<List<Ads>> getSimilarAds(String name) {
        final MutableLiveData<List<Ads>> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<List<Ads>> call = retrofit.getSimilarAds(name);
        call.enqueue(new Callback<List<Ads>>() {
            @Override
            public void onResponse(Call<List<Ads>> call, Response<List<Ads>> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponse: SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Ads>> call, Throwable t) {
                Timber.e("%sFailed", t.getMessage());
                // adsData.setValue(null);
            }
        });
        return adsData;
    }

    public MutableLiveData<Ads> createAd(Ads.Data ad, String token) {
        final MutableLiveData<Ads> adsData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Ads> call = retrofit.postAd(ad.getCategoryName(), ad.getSubCategoryName(),
                ad.getTitle(), ad.getDescription(), ad.getPrice(),
                ad.getLocation(), ad.getIsNegotiable(), BEARER + " " + token);
        call.enqueue(new Callback<Ads>() {
            @Override
            public void onResponse(Call<Ads> call, Response<Ads> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponse: SUCCESS");
                    adsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Ads> call, Throwable t) {
                Timber.e("%sFailed", t.getMessage());
                // adsData.setValue(null);
            }
        });
        return adsData;
    }

}





