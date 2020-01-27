package com.huss.android.views.ads.singleAds.report

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.huss.android.R
import timber.log.Timber


class FragmentReport : DialogFragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        view.findViewById<TextView>(R.id.learn_more).setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://huss.ng/rule_and_regulations")
            startActivity(browserIntent)
        }

        view.findViewById<Button>(R.id.submit).setOnClickListener {
            val reason = when (view.findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId){
                view.findViewById<RadioButton>(R.id.spam).id -> resources.getString(R.string.spam)
                view.findViewById<RadioButton>(R.id.wrong_price).id -> resources.getString(R.string.wrong_price)
                view.findViewById<RadioButton>(R.id.wrong_category).id -> resources.getString(R.string.wrong_category)
                view.findViewById<RadioButton>(R.id.prepayment).id -> resources.getString(R.string.prepayment)
                else -> ""
            }
            val finalReason = "CategoryActivity: $reason \nComment: ${view.findViewById<EditText>(R.id.report_description).text}"
            if (reason.isEmpty()){
                    Toast.makeText(context, "Select at least one option", Toast.LENGTH_LONG).show()
            }else{
                /*TODO: Send to db*/
                Timber.e(finalReason)
                Toast.makeText(context, "Ad reported, thanks for the feedback", Toast.LENGTH_LONG).show()
                dismiss()
            }
        }
        return view
    }
}