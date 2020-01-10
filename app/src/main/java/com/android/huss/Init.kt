package com.android.huss

import android.app.Application
import co.paystack.android.PaystackSdk
import com.cloudinary.android.MediaManager

class Init: Application() {

        override fun onCreate(){
            super.onCreate()
            val config: MutableMap<String, Any> = HashMap()
            config["cloud_name"] = getString(R.string.cloud_name)
            MediaManager.init(this, config)

            /*Paystack*/
            PaystackSdk.initialize(this)

        }


}