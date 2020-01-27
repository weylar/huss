package com.huss.android.views.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import com.huss.android.R
import com.huss.android.utility.NetworkReceiverUtil
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity(), NetworkReceiverUtil.ConnectivityReceiverListener {
private var snackbar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    fun goBack(view: View) {
        finish()
    }

    override fun onResume() {
        super.onResume()
        NetworkReceiverUtil.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            snackbar = Snackbar.make(tool_bar, "No internet connection", Snackbar.LENGTH_INDEFINITE)
            snackbar?.show()
        } else {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
        }
    }
}