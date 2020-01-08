package com.android.huss.views.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.huss.R
import com.android.huss.utility.Utility.PASSWORD_LIMITATION
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
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
        }


    }

    private fun validatePassword(password: String): Boolean {
        return password.length > PASSWORD_LIMITATION

    }
}
