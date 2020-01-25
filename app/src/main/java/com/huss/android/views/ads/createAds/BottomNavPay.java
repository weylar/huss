package com.huss.android.views.ads.createAds;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;

import com.huss.android.R;
import com.huss.android.models.Banks;
import com.huss.android.viewModels.PaymentViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import app.ephod.pentecost.library.paystack.PaymentView;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

import static com.huss.android.utility.Utility.FAILED;
import static com.huss.android.utility.Utility.SUCCESS;
import static com.huss.android.utility.Utility.TOP_ADS_PAYMENT_AMOUNT;


public class BottomNavPay extends BottomSheetDialogFragment {
    private PaymentViewModel paymentViewModel;
    private PaymentView paymentView;
    private PayState payState;
    private String errorText;

    @Override
    public void onCancel(@NotNull DialogInterface dialog)
    {
        super.onCancel(dialog);
        payState.hasPaid(false);
        Snackbar snackbar = Snackbar.make(paymentView, "Payment cancelled", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        payState = (PayState)context;
    }
    @Override
    public void setupDialog(Dialog dialog, int style) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pay, null);
        dialog.setContentView(view);

        paymentView = view.findViewById(R.id.paymentView);
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        paymentViewModel.init("emandate", true);
        paymentView.showLoader();
        paymentViewModel.getBanks().observe(this, arrayOfBanks -> {
            paymentView.hideLoader();
            paymentView.setBanksSpinner(convertToPrimitiveArray(arrayOfBanks));

        });

        paymentView.setBillContent(TOP_ADS_PAYMENT_AMOUNT);
        paymentView.getPayButton().setOnClickListener(v -> {
            if (!paymentView.getCardNumber().equals("") && !paymentView.getCardExpDate().equals("") &&
                    !paymentView.getCardCCV().equals("")) {
                String cardNumber = paymentView.getCardNumber();
                int expiryMonth = Integer.parseInt(paymentView.getCardExpDate().split("/")[0]);
                int expiryYear = Integer.parseInt(paymentView.getCardExpDate().split("/")[1]);
                String cvv = paymentView.getCardCCV();

                Card card = new Card(cardNumber, expiryMonth, expiryYear, cvv);
                if (card.isValid()) {
                    performCharge(card);
                } else {
                    Snackbar snackbar = Snackbar.make(paymentView, "Invalid card details", Snackbar.LENGTH_LONG);
                            snackbar.setAction("Retry", click -> snackbar.dismiss());
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                }
            } else {
                Snackbar snackbar = Snackbar.make(paymentView, "One or more field is empty", Snackbar.LENGTH_LONG);
                        snackbar.setAction("Retry", click -> snackbar.dismiss());
                snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));

                snackbar.show();
            }
        });

    }


    private String[] convertToPrimitiveArray(List<Banks.Datum> array) {
        String[] ret = new String[array.size()];
        Iterator<Banks.Datum> iterator = array.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().getName();
        }
        return ret;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setOnShowListener(dialogInterface -> {
            BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
            CoordinatorLayout rootView = d.findViewById(R.id.rootView);
            LinearLayout view = d.findViewById(R.id.view);
            BottomSheetBehavior.from((View) rootView.getParent()).setPeekHeight(view.getHeight());
            rootView.getParent().requestLayout();
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void performCharge(Card card) {
        Charge charge = new Charge();
        charge.setCard(card);
        charge.setAmount(Integer.parseInt(TOP_ADS_PAYMENT_AMOUNT.substring(1)) * 100);
        charge.setEmail("idrisaminu861@gmail.com");
        charge.setReference("ChargedFromAndroid_" + Calendar.getInstance().getTimeInMillis());
        try {
            charge.putCustomField("Charged From", "Android SDK");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        paymentView.showLoader();

        PaystackSdk.chargeCard(getActivity(), charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                paymentView.hideLoader();
                dismiss();
                showPaymentMessage(SUCCESS);
                payState.hasPaid(true);
            }

            @Override
            public void beforeValidate(Transaction transaction) {

            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                errorText = error.getMessage();
                paymentView.hideLoader();
                dismiss();
                showPaymentMessage(FAILED);
                payState.hasPaid(false);
                if (transaction.getReference() != null) {
                    Snackbar snackbar = Snackbar.make(paymentView, transaction.getReference() +
                            " concluded with error: " + error.getMessage(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.colorAccent));
                           snackbar.setAction("Ok", click -> snackbar.dismiss());
                    snackbar.show();

                } else {
                    Snackbar snackbar = Snackbar.make(paymentView, Objects.requireNonNull(error.getMessage()), Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.colorAccent));
                            snackbar.setAction("Ok", click -> snackbar.dismiss());
                    snackbar.show();
                }
            }

        });
    }

    private void showPaymentMessage(String result) {
        AlertDialog alertDialog= new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
        String resultText;
        Drawable icon;
        Spanned resultLongText;
        if (result.equals(SUCCESS)){
            icon = getResources().getDrawable(R.drawable.success);
            resultText = "Success";
            resultLongText = Html.fromHtml("The payment for <strong>Premium Ad</strong> was successful. " +
                    "Now you can enjoy <strong>30 days</strong> Top Ads, you get more ad visibility up to <strong>(x8)</strong> of Standard Ads.");
        }else{
            icon = getResources().getDrawable(R.drawable.faield);
            resultText = "Failed";
            resultLongText = Html.fromHtml("The attempt to make payment for <strong>Premium Ad</strong> failed. " +
                    "\nError: " + errorText);
        }
        alertDialog.setTitle(resultText);
        alertDialog.setMessage(resultLongText);
        alertDialog.setIcon(icon);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dismiss());
        alertDialog.show();
    }

    interface PayState{

        void hasPaid(boolean state);
    }
}


