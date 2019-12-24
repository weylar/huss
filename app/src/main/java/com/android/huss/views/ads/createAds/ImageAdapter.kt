package com.android.huss.views.ads.createAds


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.Collections.swap
import kotlin.collections.ArrayList
import android.view.MotionEvent
import androidx.core.view.MotionEventCompat
import android.view.View.OnTouchListener
import android.widget.Toast
import java.util.*


class ImageAdapter(private val context: Context, private val dataList: ArrayList<Uri>?)
    : RecyclerView.Adapter<ImageAdapter.CustomViewHolder>(){




    inner class CustomViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.findViewById(com.android.huss.R.id.image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(com.android.huss.R.layout.image_view, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val builder = Picasso.Builder(context)
        holder.image.clipToOutline = true
        builder.build().load(dataList!![position])
                .placeholder(com.android.huss.R.drawable.ic_launcher_background)
                .error(com.android.huss.R.drawable.ic_launcher_background)
                .into(holder.image)

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


