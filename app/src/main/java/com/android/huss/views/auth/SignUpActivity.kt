package com.android.huss.views.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.android.huss.R
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailLayout
import kotlinx.android.synthetic.main.activity_login.passwordLayout
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {


    private val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
    private val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
    private var matcher: Matcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun gotoSignIn(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun validateEmail(email: String?): Boolean {
        matcher = pattern.matcher(email)
        return matcher!!.matches()
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > 6

    }

    fun signUp(view: View) {
        val firstName: String = firstNameLayout.editText?.text.toString()
        val lastName: String = lastNameLayout.editText?.text.toString()
        val email: String = emailLayout.editText?.text.toString()
        val password: String = passwordLayout.editText?.text.toString()
        val confirmPassword: String = confirmPasswordLayout.editText?.text.toString()

        if (!validateEmail(email)) {
            emailLayout.error = "Not a valid email address!"
        }
        if (!validatePassword(password)) {
            passwordLayout.error = "Not a valid password!"
        }
        if (firstName.isEmpty()){
            firstNameLayout.error = "First name cannot be empty!"
        }

        if (lastName.isEmpty()){
            lastNameLayout.error = "Last name cannot be empty!"
        }
        if (confirmPassword.isEmpty()){
            confirmPasswordLayout.error = "Password cannot be empty!"
        }
        if (confirmPassword != password){
            confirmPasswordLayout.error = "Password do not match!"
        }

        if (validateEmail(email) and validatePassword(password)
                and firstName.isNotEmpty() and lastName.isNotEmpty() and
                (confirmPassword == password)) {
            doSignUp()
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
        if (confirmPassword.isNotEmpty() and (confirmPassword == password)){
            confirmPasswordLayout.isErrorEnabled = false
        }
    }

    private fun doSignUp() {
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

}
