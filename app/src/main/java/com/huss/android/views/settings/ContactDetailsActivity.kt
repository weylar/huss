package com.huss.android.views.settings

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.android.material.snackbar.Snackbar
import com.huss.android.R
import com.huss.android.models.Profile
import com.huss.android.utility.NetworkReceiverUtil
import com.huss.android.utility.Utility.*
import com.huss.android.viewModels.ProfileViewModel
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contact_details.*
import kotlinx.android.synthetic.main.activity_create_ads.*
import java.util.*


class ContactDetailsActivity : AppCompatActivity(), NetworkReceiverUtil.ConnectivityReceiverListener {

    private val REQUEST_CODE_READ_STORAGE = 100
    private var uri: Uri? = null
    private var progress: AlertDialog? = null
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
         progress = AlertDialog.Builder(this).create()

        locateMe()
        if (intent.hasExtra("VALUE")) {
            location.setText(intent.getStringExtra("VALUE"))
        }


        loadOtherDetails()

    }

    private fun loadOtherDetails() {
        firstName.setText(getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                .getString(USER_NAME, "")!!.split(" ")[0])
        lastName.setText(getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                .getString(USER_NAME, "")!!.split(" ")[1])
        phone.setText(getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                .getString(PHONE, ""))
        email.setText(getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                .getString(EMAIL, ""))
        email.isClickable = false; email.isEnabled = false
        location.setText(getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                .getString(LOCATION, ""))
        loadProfileImage(profile_img, placeholder, getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                .getString(PROFILE_IMAGE_URL,
                        DEFAULT_IMAGE)!!)

    }

    private fun loadProfileImage(imageview: View, placeholder: TextView, url: String) {
        if (url == DEFAULT_IMAGE) {
            placeholder.visibility = View.VISIBLE
            imageview.visibility = View.GONE
            placeholder.text = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                    .getString(USER_NAME, "")!![0].toString() + ""
        } else {
        placeholder.visibility = View.GONE
            imageview.visibility = View.VISIBLE

            val builder = Picasso.Builder(this)
            builder.loggingEnabled(true) .build().load(url)
                    .into(imageview as ImageView)

        }
    }

    private fun locateMe() {
        location.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= location.getRight() - location.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    startActivity(Intent(this@ContactDetailsActivity, MapsActivity::class.java)
                            .putExtra("FROM", "ContactDetailsActivity"))
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    fun goBack(view: View) {
        finish()
    }

    fun selectImage(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askForPermission()
        } else {
            showChooser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        doSom(requestCode, resultCode, resultData)
    }

    private fun showChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE)
    }

    private fun askForPermission() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        } else showChooser()
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_READ_STORAGE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    val snackbar = Snackbar.make(add, "Permission denied!", Snackbar.LENGTH_SHORT)
                    snackbar.setAction("Ok", { snackbar.dismiss() })
                    snackbar.setActionTextColor(resources.getColor(R.color.colorAccent))
                    snackbar.show()
                } else {
                    showChooser()
                }
            }
        }
    }

    private fun doSom(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    if (resultData.data != null) {
                        val uri = resultData.data
                        val builder = Picasso.Builder(this)
                        builder.build().load(uri)
                                .into(profile_img)
                        this.uri = uri
                        placeholder.visibility = View.GONE
                        profile_img.visibility = View.VISIBLE
                    }
                }
            }

        }
    }





    fun saveChanges(view: View) {
        val firstNameS: String = firstNameLayout.editText?.text.toString()
        val lastNameS: String = lastNameLayout.editText?.text.toString()
        if (firstNameS.isEmpty()){
            firstNameLayout.error = "First name can't be empty!"
            firstNameLayout.editText?.setHintTextColor(resources.getColorStateList(R.color.colorPrimary))
            firstNameLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimary))
        }
        if (lastNameS.isEmpty()){
            lastNameLayout.error = "Last name can't be empty!"
            lastNameLayout.editText?.setHintTextColor(resources.getColorStateList(R.color.colorPrimary))
            lastNameLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimary))
        }

        if (firstNameS.isNotEmpty()){
            firstNameLayout.isErrorEnabled = false
            firstNameLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimaryDark))
        }

        if (lastNameS.isNotEmpty()) {
            lastNameLayout.isErrorEnabled = false
            lastNameLayout.editText?.setTextColor(resources.getColorStateList(R.color.colorPrimaryDark))
        }
        if (firstNameS.isNotEmpty() and lastNameS.isNotEmpty()) {

            progress?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val viewCustom = View.inflate(this, R.layout.custom_progress, null)
            progress?.setView(viewCustom)
            progress?.setCancelable(true)
            progress?.show()
            val progressBar = viewCustom.findViewById<LVCircularZoom>(R.id.progress)
            progressBar.setViewColor(resources.getColor(R.color.colorAccent))
            progressBar.startAnim(100)
            val time = Calendar.getInstance().timeInMillis
            if (uri != null) {
                MediaManager.get().upload(uri)
                        .unsigned(this.getString(R.string.preset))
                        .callback(object : UploadCallback {
                            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                                val url = resultData!!["url"].toString()
                                val location = location.text.toString()
                                val phon = phone.text.toString()
                                val firstname = firstName.text.toString()
                                val lastName = lastName.text.toString()


                                val profile = Profile().Data()
                                profile.firstName = firstname
                                profile.lastName = lastName
                                profile.phoneNumber = phon
                                profile.city = location
                                profile.profileImgUrl = url
                                profile.token = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                                        .getString(TOKEN, "")
                                val profileViewModel = ViewModelProviders
                                        .of(this@ContactDetailsActivity).get(ProfileViewModel::class.java)
                                profileViewModel.initUpdateProfile(profile)
                                profileViewModel.updateProfile()
                                        .observe(this@ContactDetailsActivity, Observer<Profile> { loginResponse ->
                                    if (loginResponse.statusCode == STATUS_ACCEPTED_SUCCESS) {
                                        progress?.dismiss()
                                        saveLocally(url, firstname + " " + lastName, phon, location)
                                        Snackbar.make(phone, "Updated!", Snackbar.LENGTH_LONG).show()
                                    }
                                })


                            }


                            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

                            }

                            override fun onReschedule(requestId: String?, error: ErrorInfo?) {

                            }

                            override fun onError(requestId: String?, error: ErrorInfo?) {
                                val snackBar = Snackbar.make(email, " ${error.toString()}", Snackbar.LENGTH_LONG)
                                snackBar.setActionTextColor(resources.getColor(R.color.colorAccent))
                                snackBar.setAction("Ok") { snackBar.dismiss() }
                                snackBar.show()
                                progressUpload.visibility = View.GONE
                            }

                            override fun onStart(requestId: String?) {


                            }
                        })
                        .option("public_id", time.toString())
                        .dispatch()
            } else  {
                val location = location.text.toString()
                val phon = phone.text.toString()
                val firstname = firstName.text.toString()
                val lastName = lastName.text.toString()
                val profile = Profile().Data()
                val url = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE).getString(PROFILE_IMAGE_URL, DEFAULT_IMAGE)
                profile.firstName = firstname
                profile.lastName = lastName
                profile.phoneNumber = phon
                profile.city = location
                profile.profileImgUrl = url
                profile.token = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                        .getString(TOKEN, "")
                val profileViewModel = ViewModelProviders.of(this@ContactDetailsActivity).get(ProfileViewModel::class.java)
                profileViewModel.initUpdateProfile(profile)
                profileViewModel.updateProfile().observe(this@ContactDetailsActivity, Observer<Profile> { loginResponse ->
                    if (loginResponse.statusCode == STATUS_ACCEPTED_SUCCESS) {
                        progress?.dismiss()
                        saveLocally(url!!, firstname + " " + lastName, phon, location)
                        Snackbar.make(phone, "Updated!", Snackbar.LENGTH_LONG).show()
                    }
                })

            }
        }
    }
    fun saveLocally(imageUrl: String, name: String, phone: String, location: String){
        val sharedPref = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(USER_NAME, name)
        editor.putString(PHONE, phone)
        editor.putString(LOCATION, location)
        editor.putString(PROFILE_IMAGE_URL, imageUrl)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        NetworkReceiverUtil.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            snackbar = Snackbar.make(firstName, "No internet connection", Snackbar.LENGTH_INDEFINITE)
            snackbar?.show()
        } else {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
        }
    }

}
