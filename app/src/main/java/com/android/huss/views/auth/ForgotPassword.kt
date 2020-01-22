package com.android.huss.views.auth

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.huss.R
import com.android.huss.models.Profile
import com.android.huss.utility.Utility
import com.android.huss.viewModels.auth.ForgotPasswordViewModel
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_forgot_password.*
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
        emailLayout.editText?.setText(getSharedPreferences(Utility.MY_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Utility.EMAIL, ""))
    }

    fun sendPasswordResetLink(view: View) {
        val email: String = emailLayout.editText?.text.toString()

        if (!validateEmail(email)) {
            emailLayout.error = "Not a valid email address!"
        }
        if (validateEmail(email)) {

            emailLayout.isErrorEnabled = false
            doSendRequestLink(email)


        }

    }

    fun validateEmail(email: String?): Boolean {
        matcher = pattern.matcher(email)
        return matcher!!.matches()
    }

    private fun doSendRequestLink(email: String): Boolean {
        var result = false
        val progress = AlertDialog.Builder(this).create()
        progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val viewCustom = View.inflate(this, R.layout.custom_progress, null)
        progress.setView(viewCustom)
        progress.setCancelable(true)
        progress.show()
        val profile = Profile().Data()
        profile.email = email
        val progressBar = viewCustom.findViewById<LVCircularZoom>(R.id.progress)
        progressBar.setViewColor(resources.getColor(R.color.colorAccent))
        progressBar.startAnim(100)
        val forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)
        forgotPasswordViewModel.init(profile)
        forgotPasswordViewModel.forgotPassword().observe(this, Observer<Profile> { forgotResponse ->
            progress.dismiss()
            if (forgotResponse.statusCode == Utility.STATUS_CODE_OK) {
                sentText.visibility = VISIBLE
            }else{
                sentText.visibility = VISIBLE
                sentText.text = forgotResponse.message
            }

        })
        return result
    }
}
