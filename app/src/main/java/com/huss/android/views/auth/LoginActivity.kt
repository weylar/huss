package com.huss.android.views.auth

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
import com.huss.android.R
import com.huss.android.models.Profile
import com.huss.android.utility.Utility.*
import com.huss.android.viewModels.auth.LoginViewModel
import com.huss.android.views.home.MainActivity
import com.google.android.material.snackbar.Snackbar
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
        finish()
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


        val profile = Profile().Data()
        profile.email = email
        profile.password = password
        val loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.init(profile)
        loginViewModel.login().observe(this, Observer<Profile> { loginResponse  ->
            progress.dismiss()
            if (loginResponse.statusCode == STATUS_CODE_OK) {
                saveData(loginResponse)
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            }else{
               showSnack(loginResponse.message)
            }
        })

        }





    private fun saveData(profileData: Profile) {
        val sharedPref = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(TOKEN, profileData.data.token)
        editor.putString(USER_ID, profileData.data.id)
        editor.putString(USER_NAME, profileData.data.firstName + " " + profileData.data.lastName)
        editor.putString(EMAIL, profileData.data.email)
        editor.putString(PHONE, profileData.data.phoneNumber)
        editor.putString(PROFILE_IMAGE_URL, profileData.data.profileImgUrl)
        editor.apply()

    }


    private fun showSnack(message: String) {
        val snackbar = Snackbar.make(email, message, Snackbar.LENGTH_LONG)
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
