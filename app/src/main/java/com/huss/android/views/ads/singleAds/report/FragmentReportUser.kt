package com.huss.android.views.ads.singleAds.report

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.huss.android.R
import com.huss.android.models.Report
import com.huss.android.utility.Utility.TOKEN
import com.huss.android.utility.Utility.USER_ID
import com.huss.android.viewModels.ReportViewModel


class FragmentReportUser : DialogFragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_report_user, container, false)
        view.findViewById<TextView>(R.id.learn_more).setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("https://huss.ng/rule_and_regulations")
            startActivity(browserIntent)
        }

        view.findViewById<Button>(R.id.submit).setOnClickListener {
            val reason = when (view.findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId){
                view.findViewById<RadioButton>(R.id.offensive).id -> resources.getString(R.string.offensive)
                view.findViewById<RadioButton>(R.id.meet).id -> resources.getString(R.string.meet)
                view.findViewById<RadioButton>(R.id.prepayment).id -> resources.getString(R.string.prepayment)
                else -> ""
            }
            val finalReason = "Category: $reason \nComment: ${view.findViewById<EditText>(R.id.report_description).text}"
            if (reason.isEmpty()){
                    Toast.makeText(context, "Select at least one option", Toast.LENGTH_LONG).show()
            }else{
                val reportViewModel = ViewModelProviders.of(this.activity!!).get(ReportViewModel::class.java)
                val reportModel = Report().data
                reportModel.userId = Integer.parseInt(arguments!![USER_ID].toString())
                reportModel.reason = finalReason
                reportViewModel.initUser(arguments!![TOKEN].toString(), reportModel)
                activity?.let { it1 ->
                    reportViewModel.reportCallBackUser().observe(it1, Observer<Report>{
                        Toast.makeText(context, "Ad reported, thanks for the feedback",
                                Toast.LENGTH_LONG).show()
                        dismiss()
                    })
                }

            }
        }
        return view
    }
}