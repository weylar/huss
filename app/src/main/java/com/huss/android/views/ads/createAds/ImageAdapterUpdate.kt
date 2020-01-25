package com.huss.android.views.ads.createAds


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.huss.android.R
import com.huss.android.models.SingleAd
import com.squareup.picasso.Picasso


class ImageAdapterUpdate(private val context: Context, private val dataList: ArrayList<SingleAd.AdImage>?)
    : RecyclerView.Adapter<ImageAdapterUpdate.CustomViewHolder>() {



    inner class CustomViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.findViewById(R.id.image)
        var progress: ProgressBar = mView.findViewById(R.id.uploadProgress)
        var remove: ImageView = mView.findViewById(R.id.removeItem)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.image_view, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val builder = Picasso.Builder(context)
        holder.image.clipToOutline = true
        holder.remove.visibility = View.GONE
        builder.build().load(dataList!![position].imageUrl)
                .into(holder.image)

        holder.image.setOnClickListener {
            val fragment = BottomNav()
            val bundle = Bundle()
            bundle.putString("uri", dataList[position].imageUrl.toString())
            bundle.putInt("pos", position)
            fragment.arguments = bundle
            fragment.show((context as CreateAds).supportFragmentManager, "TAG")

        }




//        holder.remove.setOnClickListener {
//            /*Cancel upload*/
//            dataList.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position,dataList.size)
//
//        }

    }

//    fun moveItem(from: Int, to: Int) {
//        val fromImage = dataList?.get(from)
//        dataList?.removeAt(from)
//        fromImage?.let { dataList?.add(to, it) }
//        notifyDataSetChanged()
//
//    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }


}


