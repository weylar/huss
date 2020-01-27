package com.huss.android.views.message


import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.huss.android.R
import com.huss.android.utility.Utility.*
import com.huss.android.views.ads.singleAds.SingleAdsActivity
import com.huss.android.views.ads.singleAds.report.FragmentReport
import com.huss.android.views.profile.UserProfileActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_contact_details.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initView()
        sendMessage()
        optionClick()
    }

    private fun initView() {
        if (intent.hasExtra(PRODUCT)) {
            chat_header.text = "Chat about - ${intent.extras?.getString(PRODUCT)}"
            chat_header.setOnClickListener {
                val intent = Intent(this, SingleAdsActivity::class.java)
                intent.putExtra(PRODUCT_ID, intent.extras?.getString(PRODUCT_ID))
                intent.putExtra(SingleAdsActivity.NAME, intent.extras?.getString(PRODUCT))
                startActivity(intent)
            }
        } else {
            chat_header.text = "Chat about - General"
        }

        val builder = Picasso.Builder(this)
        builder.build().load(intent.extras?.getString(PROFILE_IMAGE_URL)).into(chat_image)
        chat_name.text = intent.extras?.getString(USER_NAME)
        chat_last_seen.text = intent.extras?.getString(LAST_SEEN)
    }

    private fun optionClick() {
        menu.setOnClickListener { v: View? ->
            val popup = PopupMenu(this, v!!)
            popup.menuInflater.inflate(R.menu.chat_menu, popup.menu)
            popup.setOnMenuItemClickListener { item: MenuItem ->
                if (item.itemId == R.id.view_profile) {
                    val intent = Intent(this, UserProfileActivity::class.java)
                    intent.putExtra(USER_ID, intent.extras?.getString(USER_ID))
                    startActivity(intent)
                } else if (item.itemId == R.id.clear_chat) {

                } else if (item.itemId == R.id.report_user) {
                    val fm = supportFragmentManager
                    val reportFrag = FragmentReport()
                    reportFrag.show(fm, "report_fragment")
                }
                true
            }
            popup.show()
        }
    }

    private fun sendMessage() {
        send.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                send()
                return@setOnEditorActionListener true
            }
            false
        }
        send.setOnTouchListener(View.OnTouchListener { _, event ->
            val drawableRight = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= location.right - location.compoundDrawables[drawableRight].bounds.width()) {
                    send()
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    private fun send() {
        val textMessage = send.text.toString()
        Toast.makeText(this, textMessage, Toast.LENGTH_LONG).show()
    }

    fun closeActivity(view: View) {
        finish()
    }
}
