package com.mediaPlayer.link.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mediaPlayer.link.R
import com.mediaPlayer.link.adapter.TrendingPosterViewPagerAdapter.SliderViewHolder
import com.mediaPlayer.link.databinding.ItemMoviePosterBinding
import com.mediaPlayer.link.model.netwokModels.MovieFeed
import com.mediaPlayer.link.ui.activity.MovieViewActivity
import com.mediaPlayer.link.utils.Constants
import com.mediaPlayer.link.utils.Utils
import com.squareup.picasso.Picasso

class TrendingPosterViewPagerAdapter
internal constructor(private var sliderItems: ArrayList<MovieFeed>, private val context: Context) :
    RecyclerView.Adapter<SliderViewHolder>() {


    var mBinding: ItemMoviePosterBinding? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(ItemMoviePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(feed: ArrayList<MovieFeed>?) {
        if (feed != null) {
            this.sliderItems = feed
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val feedObjects = sliderItems[position]

        Glide.with(context)
            .load(feedObjects.image[0].imageUrl)
            .error(
                Picasso.get()
                    .load(feedObjects?.image?.get(0)?.imageUrl)
                    .error(R.drawable.ic_not_found)
                    .into(mBinding!!.itemMovieImage)
            )
            .apply(Utils.getGlideRequest(context))
            .into(mBinding!!.itemMovieImage)

        with(mBinding!!) {
            txtGenres.text = feedObjects.genre.toString()
            txtTitle.text = feedObjects.movieName.toString()
            ratingBar.rating = (feedObjects.rating?.toFloat()!! / 2)
            viewMovieRating.text = "${(feedObjects.rating!!.toString().toDouble() / 2)}"
        }



        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieViewActivity::class.java)
            intent.putExtra(Constants.EXTRA_MOVIE_DATA, feedObjects)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SliderViewHolder(itemView: ItemMoviePosterBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        init {
            setIsRecyclable(false)
            mBinding = itemView
        }
    }

}