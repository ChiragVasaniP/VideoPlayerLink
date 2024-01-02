package com.mediaPlayer.link.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mediaPlayer.link.R
import com.mediaPlayer.link.databinding.ItemMoviePosterBinding
import com.mediaPlayer.link.model.netwokModels.MovieFeed
import com.mediaPlayer.link.ui.activity.MovieViewActivity
import com.mediaPlayer.link.utils.Constants
import com.mediaPlayer.link.utils.Utils
import com.squareup.picasso.Picasso


class SearchMovieAdapter(feed: ArrayList<MovieFeed>?, private var context: FragmentActivity?) :
    RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>(), Filterable {
    private var objectfeed: ArrayList<MovieFeed>? = feed
    private var emptyFilter: List<MovieFeed>? = feed


    var mBinding: ItemMoviePosterBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = ItemMoviePosterBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(mBinding!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedObjects = emptyFilter?.get(position)

        with(mBinding!!) {

            Glide.with(context!!)
                .load(feedObjects?.image?.get(0)?.imageUrl)
                .apply(Utils.getGlideRequest(context!!))
                .error(
                    Picasso.get()
                        .load(feedObjects?.image?.get(0)?.imageUrl)
                        .error(R.drawable.ic_not_found)
                        .into(itemMovieImage)
                )
                .into(itemMovieImage)

            txtGenres.text = feedObjects?.genre
            txtTitle.text = feedObjects?.movieName
            ratingBar.rating = (feedObjects?.rating?.toFloat()!! / 2)
            viewMovieRating.text = "${(feedObjects.rating!!.toString().toDouble() / 2)}"

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieViewActivity::class.java)
            intent.putExtra(Constants.EXTRA_MOVIE_DATA, feedObjects)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if (emptyFilter?.size != null) emptyFilter!!.size else 0
    }


    inner class ViewHolder(itemView: ItemMoviePosterBinding) :
        RecyclerView.ViewHolder(itemView.root) {
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

    fun getmobielistSize(): List<MovieFeed>? {
        return emptyFilter
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString: String = charSequence.toString()
                Log.e("TAG_search", "performFiltering: $charString ")
                if (charString.isEmpty()) {
                    emptyFilter = objectfeed
                } else {
                    val filteredList: MutableList<MovieFeed> = ArrayList<MovieFeed>()
                    for (row in objectfeed!!) {
                        if (row.movieName?.lowercase()?.contains(charString.lowercase()) == true) {
                            filteredList.add(row)
                        }
                    }
                    emptyFilter = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = emptyFilter
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {
                emptyFilter = results!!.values as List<MovieFeed>?
                notifyDataSetChanged()
            }

        }
    }

}