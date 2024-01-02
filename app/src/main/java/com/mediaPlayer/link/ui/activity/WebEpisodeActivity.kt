package com.mediaPlayer.link.ui.activity

import android.annotation.SuppressLint
import com.mediaPlayer.link.adapter.webseries.WebSeriesEpisodeAdapter
import com.mediaPlayer.link.core.BaseActivity
import com.mediaPlayer.link.databinding.ActivityWebEpisodBinding
import com.mediaPlayer.link.model.netwokModels.Sessions
import com.mediaPlayer.link.model.netwokModels.WebSeriesFeed
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.utils.Constants

class WebEpisodeActivity :
    BaseActivity<MainViewModel, ActivityWebEpisodBinding>(MainViewModel::class.java) {
    lateinit var viewModel: MainViewModel

    override fun getBinding(): ActivityWebEpisodBinding {
        return ActivityWebEpisodBinding.inflate(layoutInflater)
    }

    override fun initViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    override fun observe() {

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateBase() {
        if (intent.getSerializableExtra(Constants.EXTRA_WEB_EPISODE_DATA) != null && intent.getSerializableExtra(Constants.EXTRA_WEB_SERIES_DATA) != null) {
            val sessions = intent.getSerializableExtra(Constants.EXTRA_WEB_EPISODE_DATA) as Sessions
            val fullWebSeries =
                intent.getSerializableExtra(Constants.EXTRA_WEB_SERIES_DATA) as WebSeriesFeed
            mBinding?.txtEpisodeTitle?.text = "${fullWebSeries.movieName.toString()} Episodes"

            mBinding?.rvEpisode?.apply {
                adapter = WebSeriesEpisodeAdapter(sessions, fullWebSeries, this@WebEpisodeActivity)
            }

            mBinding?.imgBack?.setOnClickListener {
                finish()
            }
        }

    }


}