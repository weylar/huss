package com.huss.android.views.home

import android.Manifest
import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.huss.android.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        version_code.text = "Version: ${Build.VERSION_CODES.CUR_DEVELOPMENT}"
    }

    fun goBack(view: View) {
        finish()
    }
}
