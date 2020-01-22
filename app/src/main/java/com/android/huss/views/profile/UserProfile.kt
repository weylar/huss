package com.android.huss.views.profile

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.huss.R
import com.android.huss.views.home.MainActivity
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(MainActivity.checkNetworkConnection(this, profileName ), filter)
    }
    fun makeCall(view: View) {}
    fun text(view: View) {}
    fun report(view: View) {}
    fun goBack(view: View) {}
}
