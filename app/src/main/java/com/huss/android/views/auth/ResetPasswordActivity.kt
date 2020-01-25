package com.huss.android.views.auth

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.huss.android.R
import com.huss.android.models.Profile
import com.huss.android.utility.Utility
import com.huss.android.utility.Utility.PASSWORD_LIMITATION
import com.huss.android.viewModels.auth.ResetPasswordViewModel
import com.huss.android.views.home.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_reset_password.passwordLayout

class ResetPasswordActivity : AppCompatActivity() {
    var token: String? = null
    var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        handleIntent(intent)
    }


    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.path?.also { recipeId ->
               id = recipeId.split("/")[3]
               token = recipeId.split("/")[4]
            }
        }
    }

    fun resetPassword(view: View) {
        val password: String = passwordLayout.editText?.text.toString()
        val confirmPassword: String = confirmPasswordLayout.editText?.text.toString()

        if (!validatePassword(password)) {
            passwordLayout.error = "Not a valid password!"
        }
        if (confirmPassword.isEmpty()){
            confirmPasswordLayout.error = "Confirm password cannot be empty!"
        }else{
            if(confirmPassword != password){
                confirmPasswordLayout.error = "Password do not match!"
            }
        }


        if(validatePassword(password)){
            passwordLayout.isErrorEnabled = false
        }

        if(confirmPassword.isNotEmpty() and (confirmPassword == password)){
            confirmPasswordLayout.isErrorEnabled = false
            if (id != null && token != null) {
                doReset(id, token, password, confirmPassword)
            }
        }


    }

    private fun doReset(id: String?, token: String?, password: String, confirmPassword: String){
        var result = false
        val progress = AlertDialog.Builder(this).create()
        progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val viewCustom = View.inflate(this, R.layout.custom_progress, null)
        progress.setView(viewCustom); progress.setCancelable(true); progress.show()
        val profile = Profile().Data()
        profile.id = id; profile.token = token; profile.password = password; profile.confirmPassword = confirmPassword
        val progressBar = viewCustom.findViewById<LVCircularZoom>(R.id.progress)
        progressBar.setViewColor(resources.getColor(R.color.colorAccent))
        progressBar.startAnim(100)
        val resetPasswordViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java)
        resetPasswordViewModel.init(profile)
        resetPasswordViewModel.resetPassword().observe(this, Observer<Profile> { resetResponse ->
            progress.dismiss()
            if (resetResponse.statusCode == Utility.STATUS_CODE_OK) {
                saveData(resetResponse)
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            }else{
                showSnack(resetResponse.message)
            }

        })
    }
    private fun saveData(profileData: Profile) {
        val sharedPref = getSharedPreferences(Utility.MY_PREFERENCES, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(Utility.TOKEN, profileData.data.token)
        editor.putString(Utility.USER_ID, profileData.data.id)
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

    private fun validatePassword(password: String): Boolean {
        return password.length > PASSWORD_LIMITATION

    }
}
