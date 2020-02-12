package com.huss.android.views.ads.createAds

import android.app.Dialog
import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.huss.android.R


class BottomNav : BottomSheetDialogFragment() {

    override fun setupDialog(dialog: Dialog, style: Int) {
        val view: View = LayoutInflater.from(context).inflate(R.layout.image_select_option, null)
        val enlarge = view.findViewById<View>(R.id.enlarge)
        dialog.setContentView(view)

        enlarge.setOnClickListener{
           val intent = Intent(context, FullAdImagePreview::class.java)
            intent.putExtra("uri", arguments?.getString("uri"))
            startActivity(intent)
            dismiss()
        }







    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.setOnShowListener { dialogInterface ->
            val d: BottomSheetDialog = dialogInterface as BottomSheetDialog
            val rootView: CoordinatorLayout? = d.findViewById(R.id.rootView)
            val view: LinearLayout? = d.findViewById(R.id.view)
            BottomSheetBehavior.from(rootView!!.parent as View).peekHeight = view!!.height
            rootView.parent.requestLayout()
        }

        return inflater?.let { super.onCreateView(it, container, savedInstanceState) }
    }

}