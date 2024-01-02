package com.mediaPlayer.link.ui.activity

import android.annotation.SuppressLint
import com.mediaPlayer.link.adapter.webseries.WebSeriesSessionAdapter
import com.mediaPlayer.link.core.BaseActivity
import com.mediaPlayer.link.databinding.ActivityWebSeriesSessionBinding
import com.mediaPlayer.link.model.netwokModels.WebSeriesFeed
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.utils.Constants

class WebSeriesSessionActivity :
    BaseActivity<MainViewModel, ActivityWebSeriesSessionBinding>(MainViewModel::class.java) {

    private lateinit var viewModel: MainViewModel

    override fun getBinding(): ActivityWebSeriesSessionBinding {
        return ActivityWebSeriesSessionBinding.inflate(layoutInflater)
    }

    override fun initViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateBase() {
        if (intent.getSerializableExtra(Constants.EXTRA_WEB_SERIES_DATA) != null) {
            with(mBinding!!) {
                val webSeriesFeed =
                    intent.getSerializableExtra(Constants.EXTRA_WEB_SERIES_DATA) as? WebSeriesFeed
                mBinding!!.txtSessionTitle.text = "${webSeriesFeed?.movieName.toString()} Sessions"
                if (webSeriesFeed != null) {
                    rvSession.apply {
                        adapter =
                            WebSeriesSessionAdapter(webSeriesFeed.sessions, webSeriesFeed, this@WebSeriesSessionActivity)
                        setHasFixedSize(true)
                    }
                }


                imgBack.setOnClickListener {
                    finish()
                }
            }
        }
    }

    override fun observe() {

    }
}