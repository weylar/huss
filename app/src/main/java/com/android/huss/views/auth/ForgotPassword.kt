package com.android.huss.views.auth

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import com.android.huss.R
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailLayout
import java.util.regex.Matcher
import java.util.regex.Pattern

class ForgotPassword : AppCompatActivity() {

    private val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
    private val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
    private var matcher: Matcher? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    fun sendPasswordResetLink(view: View) {
        val email: String = emailLayout.editText?.text.toString()
        if (!validateEmail(email)) {
            emailLayout.error = "Not a valid email address!"
        }
        if (validateEmail(email)) {
        /*TODO: Check if email exist in db*/
            emailLayout.isErrorEnabled = false
            if (doSendRequestLink()){
                sentText.visibility = VISIBLE
            }
        }

    }

    fun validateEmail(email: String?): Boolean {
        matcher = pattern.matcher(email)
        return matcher!!.matches()
    }

    private fun doSendRequestLink(): Boolean {
        val progress = AlertDialog.Builder(this).create()
        progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val viewCustom = View.inflate(this, R.layout.custom_progress, null)
        progress.setView(viewCustom)
        progress.setCancelable(true)
        progress.show()
        val progressBar = viewCustom.findViewById<LVCircularZoom>(R.id.progress)
        progressBar.setViewColor(resources.getColor(R.color.colorAccent))
        progressBar.startAnim(100)
        return true
    }
}
