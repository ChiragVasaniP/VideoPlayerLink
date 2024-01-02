package com.mediaPlayer.link.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mediaPlayer.link.databinding.ItemListingBinding
import com.mediaPlayer.link.model.netwokModels.MovieVideo
import com.mediaPlayer.link.utils.Utils


class QualityAdapter(private var context: Context?, videoQualityList: ArrayList<MovieVideo>) :
    RecyclerView.Adapter<QualityAdapter.ViewHolder>() {
    private var videoQualityList: ArrayList<MovieVideo>? = videoQualityList


    var mBinding: ItemListingBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = ItemListingBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(mBinding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoQuality = videoQualityList?.get(position)


        with(mBinding!!) {


            buttonPlay.text = videoQuality?.quality
            buttonPlay.setOnClickListener {
                videoQuality?.videoPath?.let { uri -> context?.let { context -> Utils.openVideoPlayer(context, uri) } }
            }


        }

    }

    override fun getItemCount(): Int {
        return if (videoQualityList?.size != null) videoQualityList!!.size else 0
    }


    inner class ViewHolder(itemView: ItemListingBinding) : RecyclerView.ViewHolder(itemView.root) {
        init {
            setIsRecyclable(false)
            mBinding = itemView
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}