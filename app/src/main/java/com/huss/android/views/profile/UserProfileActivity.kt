package com.huss.android.views.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.huss.android.R
import com.huss.android.utility.NetworkReceiverUtil
import com.huss.android.utility.Utility.*
import com.huss.android.viewModels.ads.UserAdsViewModel
import com.huss.android.views.ads.latestAds.AllLatestAdsAdapter
import com.huss.android.views.ads.singleAds.SingleAdsActivity
import com.huss.android.views.message.ChatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity(), NetworkReceiverUtil.ConnectivityReceiverListener {
    private lateinit var networkUtil: NetworkReceiverUtil
    private var userId: String? = null
    private var userName: String? = null
    private var userPhone: String? = null
    private var profileUrl: String? = null
    private var lastSeen: String? = null
    private var snackbar: Snackbar? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        userId = intent.getStringExtra(USER_ID)
        profileName.text = intent.getStringExtra(USER_NAME)
        userPhone = intent.getStringExtra(PHONE)
        profileUrl = intent.getStringExtra(PROFILE_IMAGE_URL)
        lastSeen = intent.getStringExtra(LAST_SEEN)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        networkUtil = NetworkReceiverUtil()
        registerReceiver(networkUtil, filter)
        getAllUserAds()
        checkIfPhoneExist()
        message.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(USER_ID, userId)
            intent.putExtra(USER_NAME, userName)
            intent.putExtra(USER_ID, userId)
            intent.putExtra(LAST_SEEN, lastSeen)
            intent.putExtra(PROFILE_IMAGE_URL, profileUrl)
            startActivity(intent)
        }
        loadProfileImage(profile_img_, placeholder, profileUrl.toString())


    }

    private fun checkIfPhoneExist() {
        if (userPhone!!.isEmpty()) {
            phone.visibility = View.GONE
        } else {
            phone.visibility = View.VISIBLE
            phone.setOnClickListener {
                val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$userPhone"))
                try {
                    startActivity(i)
                } catch (s: SecurityException) {
                    Toast.makeText(this, s.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getAllUserAds() {
        val userAdsViewModel = ViewModelProviders.of(this).get(UserAdsViewModel::class.java)
        val token = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE).getString(TOKEN, "")
        userAdsViewModel.initAdById(token, userId)
        userAdsViewModel.userAds.observe(this, Observer { ads ->
            recycler_all_ads.adapter = AllLatestAdsAdapter(this, ads.data)
            var activeCount = 0
            for (ad in ads.data) {
                if (ad.status == getString(R.string.active)) activeCount++
            }
            totalAds.text = "Total Ads: ${SingleAdsActivity.formatViews(ads.data.size.toString())}"
        })
    }

    private fun loadProfileImage(imageView: View, placeholder: TextView, url: String) {
        if (url == DEFAULT_IMAGE) {
            placeholder.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            placeholder.text = """${getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE).getString(USER_NAME, "")!![0]}"""
        } else {
            imageView.visibility = View.VISIBLE
            val builder = Picasso.Builder(this)
            builder.build().load(url)
                    .into(imageView as ImageView)
        }
    }

    fun goBack(view: View) {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkUtil)
    }

    override fun onResume() {
        super.onResume()
        NetworkReceiverUtil.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            snackbar = Snackbar.make(message, "No internet connection", Snackbar.LENGTH_INDEFINITE)
            snackbar?.show()
        } else {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
        }
    }
}
