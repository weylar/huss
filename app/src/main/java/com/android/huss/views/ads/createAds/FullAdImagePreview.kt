package com.android.huss.views.ads.createAds


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.huss.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ads_image_view_preview.*


/**
 * A simple [Fragment] subclass.
 */
class FullAdImagePreview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ads_image_view_preview)

        val builder = Picasso.Builder(this)
        builder.build().load(intent?.getStringExtra("uri"))
                .into(image)

    }

    fun goBack(view: View) {
        finish()
    }
}
