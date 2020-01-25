package com.huss.android.views.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.huss.android.R
import com.huss.android.utility.Utility
import com.huss.android.views.auth.ForgotPassword
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_change_password.passwordLayout

class ChangePassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
    }

    fun goBack(view: View) {
        finish()
    }
    fun saveChanges(view: View) {
        val password: String = passwordLayout.editText?.text.toString()
        val newPassword: String = newPasswordLayout.editText?.text.toString()
        val confirmNewPassword: String = confirmNewPasswordLayout.editText?.text.toString()


        if (!validatePassword(newPassword)) {
            newPasswordLayout.error = "Password can't be less than 6 characters!"
            newPasswordLayout.editText?.setHintTextColor(resources.getColorStateList(R.color.colorPrimary))
            newPasswordLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimary))
        }
        if (!validatePassword(password)) {
            passwordLayout.error = "Password can't be less than 6 characters!"
            passwordLayout.editText?.setHintTextColor(resources.getColorStateList(R.color.colorPrimary))
            passwordLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimary))
        }
        if (newPassword != confirmNewPassword) {
            confirmNewPasswordLayout.error = "Passwords do not match!"
            confirmNewPasswordLayout.editText?.setHintTextColor(resources.getColorStateList(R.color.colorPrimary))
            confirmNewPasswordLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimary))

        }
        if (validatePassword(password)){
            passwordLayout.isErrorEnabled = false
            passwordLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimaryDark))
        }
        if (validatePassword(newPassword)){
            newPasswordLayout.isErrorEnabled = false
            newPasswordLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimaryDark))
        }
        if (newPassword == confirmNewPassword){
           confirmNewPasswordLayout.isErrorEnabled = false
            confirmNewPasswordLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimaryDark))
        }

        if (validatePassword(password) and validatePassword(newPassword) and (newPassword == confirmNewPassword) ) {
            changePass(password, newPassword, confirmNewPassword)
        }
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > Utility.PASSWORD_LIMITATION

    }

    private fun changePass(pass: String, newPass: String, confirmNew: String){

    }

    fun goToForget(view: View) {
        startActivity(Intent(this, ForgotPassword::class.java))
    }
}
