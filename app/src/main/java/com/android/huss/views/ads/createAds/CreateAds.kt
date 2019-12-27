package com.android.huss.views.ads.createAds

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.huss.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_ads.*


class CreateAds : AppCompatActivity() {

    private val REQUEST_CODE_READ_STORAGE = 100
    private val arrayList = ArrayList<Uri>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_ads)
        add.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askForPermission()
            } else {
                showChooser()
            }

        }






    }


    private fun showChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE)
    }

    private fun askForPermission(){
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }else showChooser()
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_STORAGE)
    }






    fun goBack(view: View) {
        finish()
    }

    fun helpClick(view: View) {}

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

    private fun doSom(requestCode: Int, resultCode: Int, resultData: Intent?){
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    if (resultData.clipData != null) {
                        val count = resultData.clipData!!.itemCount
                        var currentItem = 0
                        while (currentItem < count) {
                            val imageUri = resultData.clipData?.getItemAt(currentItem)?.uri
                            currentItem++
                            try {
                                imageUri?.let { arrayList.add(it) }
                                imagesRecycler.visibility = View.VISIBLE
                                val adapter = ImageAdapter(this, arrayList)
                                imagesRecycler.adapter = adapter
                                imagesRecycler.layoutManager = LinearLayoutManager(this,
                                        LinearLayoutManager.HORIZONTAL, false)
                                itemTouchHelper.attachToRecyclerView(imagesRecycler)
                                if (adapter.itemCount > 1) dragDescription.visibility = View.VISIBLE


                            } catch (e: Exception) {
                            }
                        }
                    } else if (resultData.data != null) {
                        val uri = resultData.data
                        try {
                            uri?.let { arrayList.add(it) }
                            imagesRecycler.visibility = View.VISIBLE
                            val adapter = ImageAdapter(this, arrayList )
                            imagesRecycler.adapter = adapter
                            imagesRecycler.layoutManager = LinearLayoutManager(this,
                                    LinearLayoutManager.HORIZONTAL, false)
                            itemTouchHelper.attachToRecyclerView(imagesRecycler)
                            if ( adapter.itemCount > 1) dragDescription.visibility = View.VISIBLE



                        } catch (e: Exception ) {

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


}