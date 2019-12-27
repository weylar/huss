package com.android.huss.views.ads.createAds


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.huss.R
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.squareup.picasso.Picasso


class ImageAdapter(private val context: Context, private val dataList: ArrayList<Uri>?)
    : RecyclerView.Adapter<ImageAdapter.CustomViewHolder>(){




    inner class CustomViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.findViewById(R.id.image)
        var progress: ProgressBar = mView.findViewById(R.id.uploadProgress)
        var retry: ImageView = mView.findViewById(R.id.retry)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.image_view, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val builder = Picasso.Builder(context)
        holder.image.clipToOutline = true
        builder.build().load(dataList!![position])
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.image)

        holder.image.setOnClickListener {
            val fragment = BottomNav()
            val bundle = Bundle()
            bundle.putString("uri", dataList[position].toString())
            fragment.arguments = bundle
            fragment.show((context as CreateAds).supportFragmentManager, "TAG")

        }


        /*Upload Image*/
        val uploadCallBack = object: UploadCallback {
            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                holder.progress.visibility = View.GONE
                holder.image.alpha = 1f
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                holder.progress.progress = (bytes/totalBytes).toInt().times(100)

            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {

            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                holder.retry.visibility = View.VISIBLE
            }

            override fun onStart(requestId: String?) {
                holder.image.alpha = 0.5f
                holder.progress.max = 100
            }
        }

       MediaManager.get().upload(dataList[position])
                .unsigned(context.getString(R.string.preset))
                .callback(uploadCallBack)
                .dispatch()

    }

    fun moveItem(from: Int, to: Int){
        val fromImage = dataList?.get(from)
        dataList?.removeAt(from)
        if (to < from) {
            fromImage?.let { dataList?.add(to, it) }
        } else {
            fromImage?.let { dataList?.add(to - 1, it) }
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }
}


