package com.android.huss.views.auth

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.huss.R
import com.android.huss.models.Profile
import com.android.huss.utility.Utility.*
import com.android.huss.viewModels.LoginViewModel
import com.android.huss.views.home.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_create_ads.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {

    private val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
    private val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
    private var matcher: Matcher? = null
    private val MyPREFERENCES = "MyPrefs"

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
            doLogin(email, password)
        }
        if (validateEmail(email)){
            emailLayout.isErrorEnabled = false
        }

        if (validatePassword(password)){
            passwordLayout.isErrorEnabled = false
        }


    }

    private fun doLogin(email: String, password: String) {
        val progress = AlertDialog.Builder(this).create()
        progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val viewCustom = View.inflate(this, R.layout.custom_progress, null)
        progress.setView(viewCustom)
        progress.setCancelable(true)
        progress.show()
        val progressBar = viewCustom.findViewById<LVCircularZoom>(R.id.progress)
        progressBar.setViewColor(resources.getColor(R.color.colorAccent))
        progressBar.startAnim(100)

        val loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val profile = Profile().Data()
        profile.email = email
        profile.password = password
        loginViewModel.init(profile)
        loginViewModel.loginProfile.observe(this, Observer<Profile> { profileData->
           progress.dismiss()
            if (profileData.statusCode == STATUS_CODE_OK) {
                saveData(profileData)
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                showSnack(profileData.message)
            }

        })

    }

    private fun saveData(profileData: Profile) {
        val sharedPref = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(TOKEN, profileData.data.token)
        editor.putString(USER_ID, profileData.data.id)
        editor.apply()
    }

    private fun showSnack(message: String) {
        val snackbar = Snackbar.make(postAd, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
        snackbar.setActionTextColor(resources.getColor(R.color.colorAccent))
        snackbar.show()
    }

    private fun validateEmail(email: String?): Boolean {
        matcher = pattern.matcher(email)
        return matcher!!.matches()
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > PASSWORD_LIMITATION

    }
}
