package com.huss.android.utility;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager

import timber.log.Timber;

class NetworkReceiverUtil : BroadcastReceiver(){

    override  fun onReceive(context: Context, intent: Intent) {
        Timber.e("Hey")
        if (connectivityReceiverListener != null){

            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnectedOrConnecting(context))
        }
    }

    private fun isConnectedOrConnecting(context: Context) : Boolean{
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    interface ConnectivityReceiverListener{
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object{
        var connectivityReceiverListener: ConnectivityReceiverListener? =null
    }

}