package com.android.huss.views.ads.createAds

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.android.huss.utility.Utility.*
import com.android.huss.viewModels.*
import com.android.huss.views.ads.singleAds.SingleAds.ID
import com.android.huss.views.profile.Profile
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.android.material.snackbar.Snackbar
import com.ldoublem.loadingviewlib.view.LVCircularZoom
import kotlinx.android.synthetic.main.activity_create_ads.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class CreateAds : AppCompatActivity(), BottomNavPay.PayState {

    private val requestCode = 100
    private val draft = "draft"
    private val category = "category"
    private val price = "price"
    private val title = "title"
    private val description = "description"
    private val location = "location"
    private val subCategory = "subCategory"
    private val negotiable = "isNegotiable"
    private val arrayList = ArrayList<Uri>()
    private val array = ArrayList<SingleAd.AdImage>()
    var isValid = false
    lateinit var token: String


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ads)
        token = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE).getString(TOKEN, "")!!
        validateEntry()
        setUpLocation()
        disableButtonPost()
        loadFromDraft()
        if (intent.extras != null) {
            val id = intent.extras?.get(ID).toString()
            val viewModel = ViewModelProviders.of(this).get(AdsViewModel::class.java)
            viewModel.initSingleAd(token, id)
            viewModel.singleAd.observe(this, Observer {
                adTitle.setText(it.data.title)
                adDescription.setText(it.data.description)
                adLocation.setText(it.data.location)
                adPrice.setText(it.data.price)
                isNegotiable_checkbox.isChecked = it.data.isNegotiable
                setUpAdType(it.data.type == STANDARD_AD)
                postAd.text = getString(R.string.update_ad_cap)
                page_title.text = getString(R.string.update_ad)
                /*Images*/
                it.data.adImages.map { url -> array.add(url) }
                imagesRecycler.visibility = View.VISIBLE
                val adapter = ImageAdapterUpdate(this, array)
                imagesRecycler.adapter = adapter
                imagesRecycler.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL, false)
                itemTouchHelper.attachToRecyclerView(imagesRecycler)
                popSpinnerCatAndSub(it.data.categoryName, it.data.subCategoryName)

                if (adTitle.text.toString().isNotEmpty() and
                        adDescription.text.toString().isNotEmpty() and
                        adLocation.text.toString().isNotEmpty() and
                        adPrice.text.toString().isNotEmpty()) {
                    unDisableButtonPost()
                }

            })
            add_image_view.visibility = View.GONE
            add.visibility = View.GONE

        } else {
            setUpAdType(false)
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

            popSpinnerCatAndSub("Agriculture & Food", "Farm Machinery & Equipment")
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

    override fun onResume() {
        super.onResume()
        val network = object : NetworkReceiverUtil() {
            override fun onNetworkChange(state: Boolean) {
                if (!state) {
                    showSnack("No network connection!")
                }
            }


        }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(network, filter)
    }

    override fun onBackPressed() {
        saveDraft()
    }

    private fun saveDraft() {
            if (adTitle.text.isNotEmpty() or adDescription.text.isNotEmpty()
                    or adPrice.text.isNotEmpty() or (isNegotiable_checkbox.isChecked) or
                    categories.isSelected or sub_category.isSelected) {
                val info = AlertDialog.Builder(this).create()
                info.setMessage("You are about to leave this page without completing the posting process. " +
                        "Do you want to save as draft and complete later?")
                info.setTitle("Hi ${getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
                        .getString(USER_NAME, "")!!.split(" ")[0]}")
                info.setButton(DialogInterface.BUTTON_POSITIVE, "Save draft!") { _, _ ->
                    val category = categories.selectedItem.toString()
                    val subCat = sub_category.selectedItem.toString()
                    val title = adTitle.text.toString()
                    val description = adDescription.text.toString()
                    val location = adLocation.text.toString()
                    val price = adPrice.text.toString().replace(",", "")
                    val isNegotiable = isNegotiable_checkbox.isChecked

                    val sharedPreferences = getSharedPreferences(draft, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putStringSet(draft, mutableSetOf(title, description, location, price))
                    editor.putString(title, title)
                    editor.putString(this.description, description)
                    editor.putString(this.location, location)
                    editor.putString(this.price, price)
                    editor.putString(this.category, category)
                    editor.putString(subCategory, subCat)
                    editor.putBoolean(this.negotiable, isNegotiable)
                    editor.apply()
                    finish()

                }
                info.setButton(DialogInterface.BUTTON_NEGATIVE, "Delete") { _, _ -> finish() }
                info.setCancelable(true)
                info.show()


            } else {
                finish()
            }

    }

    private fun loadFromDraft() {
        val sharedPreferences = getSharedPreferences(draft, Context.MODE_PRIVATE)
        categories.post { categories.setSelection(sharedPreferences.getInt(category, 0), true) }
        sub_category.post { sub_category.setSelection(sharedPreferences.getInt(subCategory, 0), true) }
        adTitle.setText(sharedPreferences.getString(title, ""))
        adDescription.setText(sharedPreferences.getString(description, ""))
        adLocation.setText(sharedPreferences.getString(location, ""))
        adPrice.setText(sharedPreferences.getString(price, ""))
        isNegotiable_checkbox.isChecked = sharedPreferences.getBoolean(negotiable, false)
        popSpinnerCatAndSub(sharedPreferences.getString(category, "")!!,
                sharedPreferences.getString(subCategory, "")!!)
        setUpAdType(state = false)
        sharedPreferences.edit().clear().apply()
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
        startActivityForResult(intent, requestCode)
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
                requestCode)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        doSom(requestCode, resultCode, resultData)
    }

    private fun doSom(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == this.requestCode) {
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
            this.requestCode -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    val snackbar = Snackbar.make(add, "Permission denied!", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                } else {
                    showChooser()
                }
            }
        }
    }


    override fun hasPaid(state: Boolean) {
        setUpAdType(state)

    }

    private fun popSpinnerCatAndSub(categoryName: String, subCategoryName: String) {
        val progress = showProgress(false)
        val list = arrayListOf<String>()
        val categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        categoryViewModel.initAllCategory(token)
        categoryViewModel.allCategory.observe(this, Observer<Category?> { catResponse ->
            for (item in catResponse!!.data) {
                list += item!!.name
            }
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list)
            categories.adapter = adapter
            categories.setSelection(list.indexOf(categoryName))


        })


        categories?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sublist = arrayListOf<String>()
                val subCategoryViewModel = ViewModelProviders.of(this@CreateAds).get(SubCategoryViewModel::class.java)
                subCategoryViewModel.init(token, categories.getItemAtPosition(position).toString())
                subCategoryViewModel.subCategory.observe(this@CreateAds, Observer<SubCategory?> { subCatResponse ->
                    for (item in subCatResponse?.data!!) {
                        sublist += item!!.name
                    }
                    val subAdapter = ArrayAdapter<String>(this@CreateAds, android.R.layout.simple_spinner_item,
                            sublist)
                    sub_category.adapter = subAdapter
                    sub_category.setSelection(sublist.indexOf(subCategoryName))
                    progress.dismiss()
                })

            }

        }


    }

    fun disableButtonPost() {
        postAd.background = resources.getDrawable(R.drawable.button_offer_disable, Resources.getSystem().newTheme())
        postAd.setTextColor(Color.LTGRAY)
    }

    private fun unDisableButtonPost() {
        postAd.background = resources.getDrawable(R.drawable.button_offer, Resources.getSystem().newTheme())
        postAd.setTextColor(Color.WHITE)
    }

    private fun setUpLocation() {
        val list = arrayListOf<String>()
        val locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        locationViewModel.init()
        locationViewModel.locations.observe(this, Observer<List<Location?>> { locationRes: List<Location?>? ->
            for (item in locationRes!!) {
                list += item!!.location

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
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                val title = s.toString()
                if (title.length > 70) {
                    titleCount.text = "(${70 - title.length}/70)"
                    titleCount.setTextColor(Color.RED)

                } else {
                    titleCount.text = "(${title.length}/70)"
                    titleCount.setTextColor(Color.GRAY)

                }
                isValid = (adTitle.text.isNotEmpty() && adTitle.text.length <= 70
                        && adDescription.text.isNotEmpty() && adDescription.text.length <= 1200
                        && adLocation.text.isNotEmpty() && adPrice.text.isNotEmpty())

                if (isValid) unDisableButtonPost() else disableButtonPost()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
        val textWatcherDesc = object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                val desc = s.toString()
                if (desc.length > 1200) {
                    descCount.text = "(${1200 - desc.length}/1200)"
                    descCount.setTextColor(Color.RED)
                } else {
                    descCount.text = "(${desc.length}/1200)"
                    descCount.setTextColor(Color.GRAY)

                }
                isValid = (adTitle.text.isNotEmpty() && adTitle.text.length <= 70
                        && adDescription.text.isNotEmpty() && adDescription.text.length <= 1200
                        && adLocation.text.isNotEmpty() && adPrice.text.isNotEmpty())
                if (isValid) unDisableButtonPost() else disableButtonPost()


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
        val textWatcherLocation = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isValid = (adTitle.text.isNotEmpty() && adTitle.text.length <= 70
                        && adDescription.text.isNotEmpty() && adDescription.text.length <= 1200
                        && adLocation.text.isNotEmpty() && adPrice.text.isNotEmpty())
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

                isValid = (adTitle.text.isNotEmpty() && adTitle.text.length <= 70
                        && adDescription.text.isNotEmpty() && adDescription.text.length <= 1200
                        && adLocation.text.isNotEmpty() && adPrice.text.isNotEmpty())
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

            if (intent.extras == null) {
                if (arrayList.isEmpty()) {
                    val snackBar = Snackbar.make(postAd, "Select at least one(1) image before publishing Ad", Snackbar.LENGTH_LONG)
                    snackBar.setAction("Ok") {
                        snackBar.dismiss()
                    }
                    snackBar.setActionTextColor(resources.getColor(R.color.colorAccent))
                    snackBar.show()
                    return
                }
            } else {
                if (array.isEmpty() and arrayList.isEmpty()) {
                    val snackBar = Snackbar.make(postAd, "Select at least one(1) image before publishing Ad", Snackbar.LENGTH_LONG)
                    snackBar.setAction("Ok") {
                        snackBar.dismiss()
                    }
                    snackBar.setActionTextColor(resources.getColor(R.color.colorAccent))
                    snackBar.show()
                    return
                }
            }

            val category = categories.selectedItem.toString()
            val subCat = sub_category.selectedItem.toString()
            val title = adTitle.text.toString()
            val description = adDescription.text.toString()
            val location = adLocation.text.toString()
            val price = adPrice.text.toString().replace(",", "")
            val isNegotiable = isNegotiable_checkbox.isChecked
            val adType = STANDARD_AD
            val ads = Ads().data
            ads.title = title
            ads.description = description
            ads.location = location
            ads.price = price
            ads.isNegotiable = isNegotiable
            ads.type = adType
            ads.categoryName = category
            ads.subCategoryName = subCat


            val progress = showProgress(true)
            fun uploadImages(productId: Int) {
                if (intent.extras == null) {
                    for ((i, image) in arrayList.withIndex()) {
                        val uploadCallBack = object : UploadCallback {
                            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                                val createImageViewModel = ViewModelProviders.of(this@CreateAds).get(CreateImageViewModel::class.java)
                                val adImage = Image().data
                                adImage.iconUrl = resultData!!["url"].toString()
                                adImage.adId = productId
                                adImage.featured = i == 0
                                createImageViewModel.init(adImage, token)
                                createImageViewModel.createImageResponse
                                        .observe(this@CreateAds, Observer<Image> {
                                            progress.dismiss()
                                            val progressSuccess = AlertDialog.Builder(this@CreateAds)
                                            progressSuccess.setCancelable(true)
                                            progressSuccess.setTitle("Congratulations!")
                                            progressSuccess.setMessage("Your Ad has been posted! Continue to view your listings")
                                            progressSuccess.setPositiveButton("Ok") { dialog, _ ->
                                                dialog.dismiss()
                                                startActivity(Intent(this@CreateAds, Profile::class.java))
                                            }
                                            progressSuccess.show()
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
                                .unsigned(this.getString(R.string.ads_upload_preset))
                                .callback(uploadCallBack)
                                .option(getString(R.string.public_id), time.toString())
                                .dispatch()

                    }
                }
            }

//            fun updateImage(singleAd: SingleAd.Data) {
//                if (arrayList.isNotEmpty()) {
//                    for ((i, image) in arrayList.withIndex()) {
//                        val uploadCallBack = object : UploadCallback {
//                            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
//                                Timber.e(resultData!!["version"].toString())
//                                progress.dismiss()
//                                val progressSuccess = AlertDialog.Builder(this@CreateAds)
//                                progressSuccess.setCancelable(true)
//                                progressSuccess.setTitle("Congratulations!")
//                                progressSuccess.setMessage("Your Ad has been updated successfully! Continue to view your Ad")
//                                progressSuccess.setPositiveButton("Ok") { dialog, _ ->
//                                    dialog.dismiss()
//                                    startActivity(Intent(this@CreateAds, Profile::class.java))
//                                }
//                                progressSuccess.show()
//
//
//                            }
//
//                            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
//
//                            }
//
//                            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
//
//                            }
//
//                            override fun onError(requestId: String?, error: ErrorInfo?) {
//                                val snackBar = Snackbar.make(postAd, " ${error.toString()}", Snackbar.LENGTH_LONG)
//                                snackBar.setActionTextColor(resources.getColor(R.color.colorAccent))
//                                snackBar.setAction("Ok") { snackBar.dismiss() }
//                                snackBar.show()
//                                progressUpload.visibility = View.GONE
//                                Timber.e(error?.description)
//                            }
//
//                            override fun onStart(requestId: String?) {
//
//
//                            }
//                        }
//
//                        fun getPublicIdFromUrl(url: String): String{
//                            val arr =  url.split("/")
//                            val lastSegment = arr[arr.size - 1]
//                            return lastSegment.substring(0, lastSegment.indexOf("."))
//
//                        }
//
//
//                            val time = Calendar.getInstance().timeInMillis
//                            MediaManager.get().upload(image)
//                                    .unsigned(this.getString(R.string.ads_upload_preset))
//                                    .callback(uploadCallBack)
//                                    .option(getString(R.string.public_id), time.toString())
//                                    .dispatch()
//
//
//
//
//
//
//                    }
//                } else {
//                    for (image in array) {
//                        progress.dismiss()
//                        val progressSuccess = AlertDialog.Builder(this@CreateAds)
//                        progressSuccess.setCancelable(true)
//                        progressSuccess.setTitle("Congratulations!")
//                        progressSuccess.setMessage("Your Ad has been updated successfully! Continue to view Ad")
//                        progressSuccess.setPositiveButton("Ok") { dialog, _ ->
//                            dialog.dismiss()
//                            startActivity(Intent(this@CreateAds, Profile::class.java))
//                        }
//                        progressSuccess.show()
//
//
//                    }
//                }
//            }


            val network = object : NetworkReceiverUtil() {
                override fun onNetworkChange(state: Boolean) {
                    if (!state) {
                        showSnack("You not connected to the internet, connect and retry")
                    } else {
                        if (intent.extras == null) {
                            val createAdViewModel = ViewModelProviders.of(this@CreateAds).get(CreateAdViewModel::class.java)
                            createAdViewModel.init(ads, token)
                            createAdViewModel.createResponse.observe(this@CreateAds, Observer<Ads> { productId ->
                                uploadImages(productId.data.id)

                            })

                        } else {
                            val singleAd = SingleAd().data
                            singleAd.id = intent.extras?.getInt(ID)
                            singleAd.categoryName = categories.selectedItem.toString()
                            singleAd.subCategoryName = sub_category.selectedItem.toString()
                            singleAd.title = adTitle.text.toString()
                            singleAd.description = adDescription.text.toString()
                            singleAd.price = adPrice.text.toString().replace(",", "")
                            singleAd.isNegotiable = isNegotiable_checkbox.isChecked
                            singleAd.type = STANDARD_AD
                            singleAd.adImages = array
                            val updateAdViewModel = ViewModelProviders.of(this@CreateAds).get(UpdateAdViewModel::class.java)
                            updateAdViewModel.init(token, singleAd)
                            updateAdViewModel.updateAdResponse.observe(this@CreateAds, Observer<SingleAd> {
                                progress.dismiss()
                                val progressSuccess = AlertDialog.Builder(this@CreateAds)
                                progressSuccess.setCancelable(true)
                                progressSuccess.setTitle("Congratulations!")
                                progressSuccess.setMessage("Your Ad has been updated successfully! Continue to view your Ad")
                                progressSuccess.setPositiveButton("Ok") { dialog, _ ->
                                    dialog.dismiss()
                                    startActivity(Intent(this@CreateAds, Profile::class.java))
                                }
                                progressSuccess.show()

                            })
                        }
                    }
                }


            }
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            registerReceiver(network, filter)
        }
    }


    private fun showProgress(cancelable: Boolean): AlertDialog {
        val progress = AlertDialog.Builder(this).create()
        progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val viewCustom = View.inflate(this, R.layout.custom_progress, null)
        progress.setView(viewCustom)
        progress.setCancelable(cancelable)
        progress.show()
        val progressBar = viewCustom.findViewById<LVCircularZoom>(R.id.progress)
        progressBar.setViewColor(resources.getColor(R.color.colorAccent))
        progressBar.startAnim(100)
        return progress
    }

    fun showSnack(text: String) {
        val snackbar = Snackbar.make(postAd, text, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Ok") { snackbar.dismiss() }
        snackbar.setActionTextColor(postAd.resources.getColor(R.color.colorAccent))
        snackbar.show()
    }

}