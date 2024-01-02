package com.mediaPlayer.link.adapter.webseries

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mediaPlayer.link.R
import com.mediaPlayer.link.databinding.ItemWebseriesBinding
import com.mediaPlayer.link.model.netwokModels.Sessions
import com.mediaPlayer.link.model.netwokModels.WebSeriesFeed
import com.mediaPlayer.link.ui.activity.WebEpisodeActivity
import com.mediaPlayer.link.utils.Constants
import com.mediaPlayer.link.utils.Utils
import com.squareup.picasso.Picasso


class WebSeriesSessionAdapter(private var feed: ArrayList<Sessions>, private var webSeriesFeed: WebSeriesFeed?, private var context: FragmentActivity?) :
    RecyclerView.Adapter<WebSeriesSessionAdapter.ViewHolder>() {


    var mBinding: ItemWebseriesBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = ItemWebseriesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(mBinding!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedObjects = feed[position]

        with(mBinding!!) {
            Glide.with(context!!)
                .load(feedObjects.sessionImg)
                .error(
                    Picasso.get()
                        .load(feedObjects.sessionImg)
                        .error(R.drawable.ic_not_found)
                        .into(itemSeriesImage)
                )
                .apply(Utils.getGlideRequest(context!!))
                .into(itemSeriesImage)

            txtTitle.text = webSeriesFeed?.movieName.toString()
            txtSessionNumber.text = feedObjects.sessionnumber.toString()

            holder.itemView.setOnClickListener {
                val intent = Intent(context, WebEpisodeActivity::class.java)
                intent.putExtra(Constants.EXTRA_WEB_EPISODE_DATA, feedObjects)
                intent.putExtra(Constants.EXTRA_WEB_SERIES_DATA, webSeriesFeed)
                context!!.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return feed.size
    }


    inner class ViewHolder(itemView: ItemWebseriesBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        init {
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