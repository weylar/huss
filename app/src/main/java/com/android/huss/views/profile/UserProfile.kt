package com.android.huss.views.profile

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.huss.R
import com.android.huss.utility.Utility
import com.android.huss.utility.Utility.TOKEN
import com.android.huss.utility.Utility.USER_ID
import com.android.huss.viewModels.UserAdsViewModel
import com.android.huss.views.ads.singleAds.SingleAds
import com.android.huss.views.home.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.placeholder

class UserProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        profileName.text = getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(Utility.USER_NAME, "")
        val phoneN = getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(Utility.PHONE, "")
        if (phoneN!!.isEmpty()) {
            phone.visibility = View.GONE
        } else {
            phone.visibility = View.VISIBLE
            phone.text = phoneN
        }

        getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(Utility.PROFILE_IMAGE_URL,
                Utility.DEFAULT_IMAGE)?.let { loadProfileImage(profile_img_, placeholder, it) }
        val userAdsViewModel = ViewModelProviders.of(this).get(UserAdsViewModel::class.java)
        val token =  getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(TOKEN, "")
        userAdsViewModel.initAdById(token, intent.getStringExtra(USER_ID))
        userAdsViewModel.userAds.observe(this, Observer { ads ->
            recycler_all_ads.adapter = ActiveAdsAdapter(this, ads.data)
            var activeCount = 0
            for (ad in ads.data) {
                if (ad.status == getString(R.string.active)) activeCount++
            }
            totalAds.text = "Total Ads By User: " + SingleAds.formatViews( ads.data.size.toString() + "")
            all_ads_label.visibility = View.VISIBLE
        })



    }
    private fun loadProfileImage(imageview: View, placeholder: TextView, url: String) {
        if (url == Utility.DEFAULT_IMAGE) {
            placeholder.visibility = View.VISIBLE
            imageview.visibility = View.GONE
            placeholder.text = """${getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(Utility.USER_NAME, "")!![0]}"""
        } else {
            imageview.visibility = View.VISIBLE
            val builder = Picasso.Builder(this)
            builder.build().load(url)
                    .into(imageview as ImageView)
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(MainActivity.checkNetworkConnection(this, profileName ), filter)
    }


    fun goBack(view: View) {}
}
