package com.huss.android.views.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.huss.android.R

class AllChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_chat)
    }

    fun goBack(view: View) {}
}
