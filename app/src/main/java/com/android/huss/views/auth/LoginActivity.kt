package com.android.huss.views.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.huss.R
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {

    private val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
    private val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
    private var matcher: Matcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goToForget(view: View) {
        startActivity(Intent(this, ForgotPassword::class.java))
    }
    fun gotoSignUp(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    fun signIn(view: View) {

        val email: String = emailLayout.editText?.text.toString()
        val password: String = passwordLayout.editText?.text.toString()

        if (!validateEmail(email)) {
            emailLayout.error = "Not a valid email address!"
        }
        if (!validatePassword(password)) {
            passwordLayout.error = "Not a valid password!"
        }
        if (validateEmail(email) and validatePassword(password) ) {
            doLogin()
        }
        if (validateEmail(email)){
            emailLayout.isErrorEnabled = false
        }

        if (validatePassword(password)){
            passwordLayout.isErrorEnabled = false
        }


    }

    private fun doLogin() {
        val progress = AlertDialog.Builder(this).create()
        progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val viewCustom = View.inflate(this, R.layout.custom_progress, null)
        progress.setView(viewCustom)
        progress.setCancelable(true)
        progress.show()
        val progressBar = viewCustom.findViewById<LVCircularZoom>(R.id.progress)
        progressBar.setViewColor(resources.getColor(R.color.colorAccent))
        progressBar.startAnim(100)
    }

    fun validateEmail(email: String?): Boolean {
        matcher = pattern.matcher(email)
        return matcher!!.matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length > 6

    }
}
