package com.huss.android.views.profile

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
import com.huss.android.R
import com.huss.android.utility.Utility
import com.huss.android.utility.Utility.TOKEN
import com.huss.android.utility.Utility.USER_ID
import com.huss.android.viewModels.UserAdsViewModel
import com.huss.android.views.ads.latestAds.AllLatestAdsAdapter
import com.huss.android.views.ads.singleAds.SingleAds
import com.huss.android.views.home.MainActivity
import com.huss.android.views.message.ChatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_profile.*

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
            phone.setOnClickListener {
                val u = Uri.parse("tel:$phoneN")
                val i = Intent(Intent.ACTION_DIAL, u)
                try {
                    startActivity(i)
                } catch (s: SecurityException) {
                    Toast.makeText(this, s.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        message.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

        getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(Utility.PROFILE_IMAGE_URL,
                Utility.DEFAULT_IMAGE)?.let { loadProfileImage(profile_img_, placeholder, it) }
        val userAdsViewModel = ViewModelProviders.of(this).get(UserAdsViewModel::class.java)
        val token =  getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE).getString(TOKEN, "")
        userAdsViewModel.initAdById(token, intent.getStringExtra(USER_ID))
        userAdsViewModel.userAds.observe(this, Observer { ads ->
            recycler_all_ads.adapter = AllLatestAdsAdapter(this, ads.data)
            var activeCount = 0
            for (ad in ads.data) {
                if (ad.status == getString(R.string.active)) activeCount++
            }
            totalAds.text = "Total Ads: " + SingleAds.formatViews( ads.data.size.toString() + "")
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
