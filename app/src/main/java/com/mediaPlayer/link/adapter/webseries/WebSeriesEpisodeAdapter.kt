package com.mediaPlayer.link.adapter.webseries

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.mediaPlayer.link.databinding.ItemListingBinding
import com.mediaPlayer.link.model.netwokModels.Sessions
import com.mediaPlayer.link.model.netwokModels.WebSeriesFeed
import com.mediaPlayer.link.ui.activity.EpisodeViewActivity
import com.mediaPlayer.link.utils.Constants


class WebSeriesEpisodeAdapter(private var sessions: Sessions, private var webSeriesFeed: WebSeriesFeed?, private var context: FragmentActivity?) :
    RecyclerView.Adapter<WebSeriesEpisodeAdapter.ViewHolder>() {


    var mBinding: ItemListingBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = ItemListingBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(mBinding!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = sessions.episode[position]



        with(mBinding!!) {
//            Glide.with(context!!)
//                .load(webSeriesFeed?.sessions?.get(0)?.sessionImg)
//                .apply(Utils.getGlideRequest(context!!))
//                .into(itemSeriesImage)
//
//            txtTitle.text = webSeriesFeed?.movieName.toString()
//            txtSessionNumber.text = episode.episod.toString()
            buttonPlay.text = episode.episod.toString()

            buttonPlay.setOnClickListener {
                val intent = Intent(context, EpisodeViewActivity::class.java)
                intent.putExtra(Constants.EXTRA_WEB_EPISODE_DATA, episode)
                intent.putExtra(Constants.EXTRA_WEB_SERIES_DATA, webSeriesFeed)
                context!!.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return sessions.episode.size
    }


    inner class ViewHolder(itemView: ItemListingBinding) : RecyclerView.ViewHolder(itemView.root) {
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