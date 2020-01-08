package com.android.huss.views.ads.createAds

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.huss.R
import com.android.huss.models.*
import com.android.huss.utility.NetworkReceiverUtil
import com.android.huss.viewModels.*
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.android.material.snackbar.Snackbar
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_create_ads.*
import kotlinx.android.synthetic.main.custom_progress_add_ad.*
import java.io.File
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateAds : AppCompatActivity(), BottomNavPay.PayState {

    private val REQUEST_CODE_READ_STORAGE = 100
    private val arrayList = ArrayList<Uri>()
    var isValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ads)
        setUpAdType(false)
        popSpinnerCatAndSub()
        validateEntry()
        setUpLocation()
        disableButtonPost()


        add.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askForPermission()
            } else {
                showChooser()
            }
            terms.setOnClickListener {
                openTerms()

            }
            typeInfo.setOnClickListener {
                showInfo()
            }

        }

        postAd.setOnClickListener {
            publishAd()
        }
        payCheck.setOnClickListener {
            /*TODO: Check if user has paid, if true don't show this again*/
//            val fragment = BottomNavPay()
//                fragment.show(supportFragmentManager, "TAG")
        }

    }


    private fun setUpAdType(state: Boolean) {
        if (state) radioGroup.check(R.id.payCheck)
        else radioGroup.check(R.id.freeCheck)
    }

    private fun openTerms() {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse("https://huss.ng/terms_and_conditions")
        startActivity(browserIntent)
    }

    private fun showInfo() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("What is Ad Type?")
        alertDialog.setMessage("This is used to define the type pf ad you want to run on Huss.ng." +
                "Standard ad is a free ad that runs for seven 7 and gets into inactive mode automatically " +
                "until you.....bla bla bla" +
                "explicitly\n" +
                "Alternatively Top ads(Paid) ....bla bla bla")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }


    private fun showChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
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


    fun goBack(view: View) {
        finish()
    }

    fun helpClick(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse("https://huss.ng/help")
        startActivity(browserIntent)
    }

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or
                        ItemTouchHelper.DOWN or
                        ItemTouchHelper.START or
                        ItemTouchHelper.END, 0) {

            override fun onMove(recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {

                val adapter = recyclerView.adapter as ImageAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                adapter.moveItem(from, to)
                adapter.notifyItemMoved(from, to)

                return true
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                  direction: Int) {
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)

                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)

                viewHolder.itemView.alpha = 1.0f
            }
        }

        ItemTouchHelper(simpleItemTouchCallback)
    }

    fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        doSom(requestCode, resultCode, resultData)
    }

    private fun doSom(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    if (resultData.clipData != null) {
                        var count = resultData.clipData!!.itemCount
                        if (count > 4) {
                            Snackbar.make(add, "Only the first 4 of $count images will be uploaded ", Snackbar.LENGTH_LONG).show()
                            count = 4
                        }
                        var currentItem = 0
                        while (currentItem < count) {
                            val imageUri = resultData.clipData?.getItemAt(currentItem)?.uri
                            currentItem++
                            try {

                                if (arrayList.size < 4) {
                                    imageUri?.let { arrayList.add(it) }
                                } else Snackbar.make(add, "You have reached the maximum number of image upload",
                                        Snackbar.LENGTH_LONG).show()
                                imagesRecycler.visibility = View.VISIBLE
                                val adapter = ImageAdapter(this, arrayList)

                                imagesRecycler.adapter = adapter
                                imagesRecycler.layoutManager = LinearLayoutManager(this,
                                        LinearLayoutManager.HORIZONTAL, false)
                                itemTouchHelper.attachToRecyclerView(imagesRecycler)
                                if (adapter.itemCount > 1) dragDescription.visibility = View.VISIBLE
                                else dragDescription.visibility = View.GONE


                            } catch (e: Exception) {
                            }
                        }
                    } else if (resultData.data != null) {
                        val uri = resultData.data
                        val file = File(getRealPathFromURI(uri.toString()))
                        try {
                            if (arrayList.size > 3) {
                                Snackbar.make(add, "You have reached the maximum number of image upload", Snackbar.LENGTH_LONG).show()
                                return
                            }
                            uri?.let { arrayList.add(it) }
                            imagesRecycler.visibility = View.VISIBLE
                            val adapter = ImageAdapter(this, arrayList)

                            imagesRecycler.adapter = adapter
                            imagesRecycler.layoutManager = LinearLayoutManager(this,
                                    LinearLayoutManager.HORIZONTAL, false)
                            itemTouchHelper.attachToRecyclerView(imagesRecycler)
                            if (adapter.itemCount > 1) dragDescription.visibility = View.VISIBLE
                            else dragDescription.visibility = View.GONE

                        } catch (e: Exception) {

                        }
                    }
                }
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_READ_STORAGE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    val snackbar = Snackbar.make(add, "Permission denied!", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                } else {
                    showChooser()
                }
            }
        }
    }

    fun getRealPathFromURI(contentURI: String): String {
        val contentUri = Uri.parse(contentURI)
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = contentResolver.query(contentUri, projection, null, null, null)

        val columnIndex = cursor?.getColumnIndex(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = cursor?.getString(columnIndex!!).toString()
        cursor?.close()
        Log.e("Path", path)
        return path
    }

    override fun hasPaid(state: Boolean) {
        setUpAdType(state)

    }

    fun popSpinnerCatAndSub() {
        val list = arrayListOf<String>()
        val categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        categoryViewModel.init()
        categoryViewModel.category.observe(this, Observer<List<Category?>> { catResponse: List<Category?>? ->
            for (item in catResponse!!) {
//                list += item!!.name
            }

        })
        list += "Mobile"
        list += "Auto"
        list += "Housing"
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                list)
        categories.adapter = adapter
        categories?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sublist = arrayListOf<String>()
                val subCategoryViewModel = ViewModelProviders.of(this@CreateAds).get(SubCategoryViewModel::class.java)
                subCategoryViewModel.init(categories.getItemAtPosition(position).toString())
                subCategoryViewModel.subCategory.observe(this@CreateAds, Observer<List<SubCategory?>> { subCatResponse: List<SubCategory?>? ->
                    for (item in subCatResponse!!) {
//                sublist += item!!.name
                    }

                })
                sublist += "Mobile"
                sublist += "Auto"
                sublist += "Housing"
                val subAdapter = ArrayAdapter<String>(this@CreateAds, android.R.layout.simple_spinner_item,
                        sublist)
                sub_category.adapter = subAdapter
            }

        }


    }

    fun disableButtonPost() {
        postAd.background = resources.getDrawable(R.drawable.button_offer_disable)
        postAd.setTextColor(Color.LTGRAY)
    }

    private fun unDisableButtonPost() {
        postAd.background = resources.getDrawable(R.drawable.button_offer)
        postAd.setTextColor(Color.WHITE)
    }

    private fun setUpLocation() {
        val list = arrayListOf<String>()
        val locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        locationViewModel.init()
        locationViewModel.locations.observe(this, Observer<List<Location?>> { locationRes: List<Location?>? ->
            for (item in locationRes!!) {
//                list += item!!.location

            }

        })
        list += "Ikeja, Lagos"
        list += "Ikorodu, Lagos"
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        adLocation.setAdapter(adapter)
        adLocation.threshold = 1
    }

    private fun validateEntry() {
        val textWatcherTitle = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val title = s.toString()
                if (title.length > 70) {
                    titleCount.text = "(${70 - title.length}/70)"
                    titleCount.setTextColor(Color.RED)

                } else {
                    titleCount.text = "(${title.length}/70)"
                    titleCount.setTextColor(Color.GRAY)

                }
                if (adTitle.text.isNotEmpty() && adTitle.text.length <= 70
                        && categories.selectedItem != null
                        && adDescription.text.isNotEmpty() && adDescription.text.length <= 1200
                        && adLocation.text.isNotEmpty() && adPrice.text.isNotEmpty()) {
                    isValid = true
                }

                if (isValid) unDisableButtonPost() else disableButtonPost()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
        val textWatcherDesc = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val desc = s.toString()
                if (desc.length > 1200) {
                    descCount.text = "(${1200 - desc.length}/1200)"
                    descCount.setTextColor(Color.RED)
                } else {
                    descCount.text = "(${desc.length}/1200)"
                    descCount.setTextColor(Color.GRAY)

                }
                if (adTitle.text.isNotEmpty() && adTitle.text.length <= 70
                        && categories.selectedItem != null
                        && adDescription.text.isNotEmpty() && adDescription.text.length <= 1200
                        && adLocation.text.isNotEmpty() && adPrice.text.isNotEmpty()) {
                    isValid = true
                }
                if (isValid) unDisableButtonPost() else disableButtonPost()


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
        val textWatcherLocation = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val desc = s.toString()
                if (adTitle.text.isNotEmpty() && adTitle.text.length <= 70
                        && categories.selectedItem != null
                        && adDescription.text.isNotEmpty() && adDescription.text.length <= 1200
                        && adLocation.text.isNotEmpty() && adPrice.text.isNotEmpty()) {
                    isValid = true
                }
                if (isValid) unDisableButtonPost() else disableButtonPost()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
        val textWatcherPrice = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adPrice.removeTextChangedListener(this)

                try {
                    var originalString = s.toString()
                    originalString = originalString.replace(",", "")
                    val longval = Integer.parseInt(originalString)
                    val formattedString = String.format("%,d", longval)
                    adPrice.setText(formattedString)
                    adPrice.setSelection(adPrice.text.length)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }

                if (adTitle.text.isNotEmpty() && adTitle.text.length <= 70
                        && categories.selectedItem != null
                        && adDescription.text.isNotEmpty() && adDescription.text.length <= 1200
                        && adLocation.text.isNotEmpty() && adPrice.text.isNotEmpty()) {
                    isValid = true
                }
                if (isValid) unDisableButtonPost() else disableButtonPost()
                adPrice.addTextChangedListener(this)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }

        adTitle.addTextChangedListener(textWatcherTitle)
        adDescription.addTextChangedListener(textWatcherDesc)
        adPrice.addTextChangedListener(textWatcherPrice)
        adLocation.addTextChangedListener(textWatcherLocation)


    }

    private fun publishAd() {
        if (isValid) {
            if (arrayList.isEmpty()) {
                val snackbar = Snackbar.make(postAd, "Select at least one(1) image before publishing Ad", Snackbar.LENGTH_LONG);
                snackbar.setAction("Ok") {
                    snackbar.dismiss()
                }
                snackbar.setActionTextColor(resources.getColor(R.color.colorAccent))
                snackbar.show()
                return
            }
            val category = categories.selectedItem.toString()
            val subCat = sub_category.selectedItem.toString()
            val title = adTitle.text.toString()
            val description = adDescription.text.toString()
            val location = adLocation.text.toString()
            val price = adPrice.text.toString().replace(",", "")
            val isNegotiable = isNegotiable.isChecked
            val adType = "Standard Ad"
            val ads = Ads()
            ads.title = title
            ads.description = description
            ads.location = location
            ads.price = price
            ads.isNegotiable = isNegotiable
            ads.type = adType


            val progress = AlertDialog.Builder(this).create()
            progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val viewCustom = View.inflate(this, R.layout.custom_progress, null)
            progress.setView(viewCustom)
            progress.setCancelable(true)
            progress.show()
            val progressBar = viewCustom.findViewById<LVCircularZoom>(R.id.progress)
            progressBar.setViewColor(resources.getColor(R.color.colorAccent))
            progressBar.startAnim(100)

            fun uploadImages(productId: Int) {
                for (image in arrayList) {
                    val uploadCallBack = object : UploadCallback {
                        override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                            val createImageViewModel = ViewModelProviders.of(this@CreateAds).get(CreateImageViewModel::class.java)
                            val adImage = AdImage()
                            adImage.imageUrl = resultData!!["url"].toString()
                            adImage.productId = productId
                            createImageViewModel.init(adImage)
                            createImageViewModel.createImageResponse.observe(this@CreateAds, Observer<String> { imageId: String ->
                              progress.dismiss()

                            })

                        }

                        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

                        }

                        override fun onReschedule(requestId: String?, error: ErrorInfo?) {

                        }

                        override fun onError(requestId: String?, error: ErrorInfo?) {
                            val snackBar = Snackbar.make(postAd, " ${error.toString()}", Snackbar.LENGTH_LONG)
                            snackBar.setActionTextColor(resources.getColor(R.color.colorAccent))
                            snackBar.setAction("Ok") { snackBar.dismiss() }
                            snackBar.show()
                            progressUpload.visibility = View.GONE
                        }

                        override fun onStart(requestId: String?) {


                        }
                    }

                    val time = Calendar.getInstance().timeInMillis
                    MediaManager.get().upload(image)
                            .unsigned(this.getString(R.string.preset))
                            .callback(uploadCallBack)
                            .option("public_id", time.toString())
                            .dispatch()
                }
            }

            fun showSnack(text: String) {
                val snackbar = Snackbar.make(postAd, text, Snackbar.LENGTH_LONG);
                snackbar.setAction("Ok") { snackbar.dismiss() }
                snackbar.setActionTextColor(postAd.resources.getColor(R.color.colorAccent))
                snackbar.show()
            }

            val network = object : NetworkReceiverUtil() {
                override fun onNetworkChange(state: Boolean) {
                    if (!state) {
                        showSnack("You not connected to the internet, connect and retry")
                    } else {

                        val createAdViewModel = ViewModelProviders.of(this@CreateAds).get(CreateAdViewModel::class.java)
                        createAdViewModel.init(ads, category, subCat, "Bearer" + ""/*TODO: Token from shared*/)
                        createAdViewModel.createResponse.observe(this@CreateAds, Observer<Int> { catId: Int ->
                            uploadImages(catId)

                        })

                    }
                }


            }
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            registerReceiver(network, filter)
        }
    }


}