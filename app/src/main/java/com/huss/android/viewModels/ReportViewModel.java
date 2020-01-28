package com.huss.android.viewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Image;
import com.huss.android.models.Report;
import com.huss.android.repositories.CreateImageRepository;
import com.huss.android.repositories.ReportRepository;

public class ReportViewModel extends ViewModel {

    private MutableLiveData<Report> mutableLiveData;
    private MutableLiveData<Report> mutableLiveDataUser;

    public void init( String token, Report.Data report) {
        ReportRepository reportRepository = ReportRepository.getInstance();
        mutableLiveData = reportRepository.reportAd(token, report);

    }
    public void initUser( String token, Report.Data report) {
        ReportRepository reportRepository = ReportRepository.getInstance();
        mutableLiveDataUser = reportRepository.reportUser(token, report);

    }

    public LiveData<Report> reportCallBack() {
        return mutableLiveData;
    }
    public LiveData<Report> reportCallBackUser() {
        return mutableLiveData;
    }



}
