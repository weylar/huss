package com.huss.android.utility;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class NetworkReceiverUtil extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtil.getConnectivityStatusString(context)) {
            onNetworkChange(true);
        }else {
            onNetworkChange(false);
        }
    }

    protected abstract void onNetworkChange(boolean state);
}