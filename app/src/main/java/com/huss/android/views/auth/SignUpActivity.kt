package com.huss.android.views.auth

import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.huss.android.R
import com.huss.android.models.Profile
import com.huss.android.utility.NetworkReceiverUtil
import com.huss.android.utility.Utility.*
import com.huss.android.viewModels.auth.SignUpViewModel
import com.huss.android.views.home.MainActivity
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_login.emailLayout
import kotlinx.android.synthetic.main.activity_login.passwordLayout
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity(), NetworkReceiverUtil.ConnectivityReceiverListener {


    private val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
    private val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
    private var matcher: Matcher? = null
    private lateinit var networkReceiverUtil: NetworkReceiverUtil
    private  var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        networkReceiverUtil = NetworkReceiverUtil()
        registerReceiver(networkReceiverUtil, filter)

    }

    override fun onResume() {
        super.onResume()
        NetworkReceiverUtil.connectivityReceiverListener = this
    }

    fun gotoSignIn(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun validateEmail(email: String?): Boolean {
        matcher = pattern.matcher(email)
        return matcher!!.matches()
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > PASSWORD_LIMITATION

    }

    fun signUp(view: View) {
        val firstName: String = firstNameLayout.editText?.text.toString()
        val lastName: String = lastNameLayout.editText?.text.toString()
        val email: String = emailLayout.editText?.text.toString()
        val password: String = passwordLayout.editText?.text.toString()
        val confirmPassword: String = passwordLayout.editText?.text.toString()

        if (!validateEmail(email)) {
            emailLayout.error = "Not a valid email address!"
            emailLayout.editText?.setHintTextColor(Color.WHITE)
        }
        if (!validatePassword(password)) {
            passwordLayout.error = "Not a valid password!"
            passwordLayout.editText?.setHintTextColor(Color.WHITE)
        }
        if (firstName.isEmpty()){
            firstNameLayout.error = "First name cannot be empty!"
            firstNameLayout.editText?.setHintTextColor(Color.WHITE)
        }

        if (lastName.isEmpty()){
            lastNameLayout.error = "Last name cannot be empty!"
            lastNameLayout.editText?.setHintTextColor(Color.WHITE)
        }



        if (validateEmail(email) and validatePassword(password)
                and firstName.isNotEmpty() and lastName.isNotEmpty() and
                (confirmPassword == password)) {
            doSignUp(firstName, lastName, email, password, confirmPassword)
        }

        if (validateEmail(email)){
            emailLayout.isErrorEnabled = false
        }
        if (validatePassword(password)){
            passwordLayout.isErrorEnabled = false
        }
        if (firstName.isNotEmpty()){
            firstNameLayout.isErrorEnabled = false
        }
        if (lastName.isNotEmpty()){
            lastNameLayout.isErrorEnabled = false
        }

    }

    private fun doSignUp(firstName: String, lastName: String, email: String, password: String, confirmPassword: String ) {
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
        profile.firstName = firstName
        profile.lastName = lastName
        profile.confirmPassword = confirmPassword
        val loginViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        loginViewModel.init(profile)
        loginViewModel.signup().observe(this, Observer<Profile> { loginResponse  ->
            progress.dismiss()
            if (loginResponse.statusCode == STATUS_CREATED_SUCCESS) {
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
        editor.apply()
    }
    private fun showSnack(message: String) {
        val snackbar = Snackbar.make(password, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
        snackbar.setActionTextColor(resources.getColor(R.color.colorAccent))
        snackbar.show()
    }

    fun goBack(view: View) {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiverUtil)
    }



    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            snackbar = Snackbar.make(firstNameLayout, "No internet connection", Snackbar.LENGTH_INDEFINITE)
            snackbar?.show()
        } else {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
        }
    }

}
