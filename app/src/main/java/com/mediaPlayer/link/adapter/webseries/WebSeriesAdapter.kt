package com.mediaPlayer.link.adapter.webseries

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mediaPlayer.link.R
import com.mediaPlayer.link.databinding.ItemMoviePosterBinding
import com.mediaPlayer.link.model.netwokModels.WebSeriesFeed
import com.mediaPlayer.link.ui.activity.WebSeriesSessionActivity
import com.mediaPlayer.link.utils.Constants
import com.mediaPlayer.link.utils.Utils
import com.squareup.picasso.Picasso


class WebSeriesAdapter : RecyclerView.Adapter<WebSeriesAdapter.ViewHolder> {
    private var feed: ArrayList<WebSeriesFeed>? = null
    private var context: FragmentActivity? = null

    constructor()

    constructor(feed: ArrayList<WebSeriesFeed>?, context: FragmentActivity?) {
        this.context = context
        this.feed = feed

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(feed: ArrayList<WebSeriesFeed>?) {
        this.feed = feed
        notifyDataSetChanged()
    }

    var mBinding: ItemMoviePosterBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = ItemMoviePosterBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(mBinding!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedObjects = feed?.get(position)


        with(mBinding!!) {

            Glide.with(context!!)
                .load(feedObjects?.sessions?.get(0)?.sessionImg)
                .error(
                    Picasso.get()
                        .load(feedObjects?.sessions?.get(0)?.sessionImg)
                        .error(R.drawable.ic_not_found)
                        .into(itemMovieImage)
                )
                .apply(Utils.getGlideRequest(context!!))
                .into(itemMovieImage)


            txtGenres.text = feedObjects?.genre
            txtTitle.text = feedObjects?.movieName
            ratingBar.rating = (feedObjects?.rating?.toFloat()!! / 2)
            viewMovieRating.text = "${(feedObjects.rating!!.toString().toDouble() / 2)}"

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebSeriesSessionActivity::class.java)
            intent.putExtra(Constants.EXTRA_WEB_SERIES_DATA, feedObjects)
            context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return if (feed?.size != null) feed!!.size else 0
    }


    inner class ViewHolder(itemView: ItemMoviePosterBinding) :
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