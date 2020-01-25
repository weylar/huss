package com.huss.android.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huss.android.models.Banks;
import com.huss.android.repositories.PaymentRepository;

import java.util.List;

public class PaymentViewModel extends ViewModel {

    private MutableLiveData<List<Banks.Datum>> mutableLiveData;
    private PaymentRepository paymentRepository;

    public void init(String gateway, boolean isPayWithBank) {
        if (mutableLiveData != null) {
            return;
        }
        paymentRepository = PaymentRepository.getInstance();
        mutableLiveData = paymentRepository.getBanks(gateway, isPayWithBank);

    }

    public LiveData<List<Banks.Datum>> getBanks() {
        return mutableLiveData;
    }

}
