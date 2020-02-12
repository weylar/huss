package com.huss.android.repositories;

import androidx.lifecycle.MutableLiveData;

import com.huss.android.data.HussAPI;
import com.huss.android.data.RetrofitClientInstance;
import com.huss.android.models.Profile;
import com.huss.android.models.Report;
import com.huss.android.utility.Utility;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ReportRepository {

    private static final String TAG = "ProfileRepository";
    private static ReportRepository profileRepository;

    public static ReportRepository getInstance(){
        if (profileRepository == null){
            profileRepository = new ReportRepository();
        }
        return profileRepository;
    }



    public MutableLiveData<Report> reportAd(String token, Report.Data report) {
        final MutableLiveData<Report> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Report> call = retrofit.reportAd(Utility.BEARER + " " + token,
                report.getProductId(),report.getUserId(), report.getReason());
        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(@NotNull Call<Report> call, @NotNull Response<Report> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponseUserProfile: SUCCESS");
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Report> call, @NotNull Throwable t) {
                Timber.e("%sFailedUserProfile", t.getMessage());
            }
        });
        return profileData;
    }
    public MutableLiveData<Report> reportUser(String token, Report.Data report) {
        final MutableLiveData<Report> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Report> call = retrofit.reportUser(Utility.BEARER + " " + token,
                report.getUserId(), report.getReason());
        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(@NotNull Call<Report> call, @NotNull Response<Report> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponseUserProfile: SUCCESS");
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Report> call, @NotNull Throwable t) {
                Timber.e("%sFailedUserProfile", t.getMessage());
            }
        });
        return profileData;
    }

    public MutableLiveData<Profile> updateProfile(Profile.Data profile ) {
        final MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.updateUserProfile(Utility.BEARER + " " + profile.getToken(),
                profile.getProfileImgUrl(),
                profile.getFirstName(), profile.getLastName(), profile.getPhoneNumber(),
                profile.getCity());
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NotNull Call<Profile> call, @NotNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponseUserProfile: SUCCESS");
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Profile> call, @NotNull Throwable t) {
                Timber.e("%sFailedUserProfile", t.getMessage());
            }
        });
        return profileData;
    }

 public MutableLiveData<Profile> updateLastSeen(String token, boolean isOnline ) {
        final MutableLiveData<Profile> profileData = new MutableLiveData<>();
        HussAPI retrofit = RetrofitClientInstance.getRetrofitInstance().create(HussAPI.class);
        Call<Profile> call = retrofit.updateLastSeen(Utility.BEARER + " " + token, isOnline);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NotNull Call<Profile> call, @NotNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    Timber.e("onResponseUserProfile: SUCCESS");
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Profile> call, @NotNull Throwable t) {
                Timber.e("%sFailedUserProfile", t.getMessage());
            }
        });
        return profileData;
    }



}





