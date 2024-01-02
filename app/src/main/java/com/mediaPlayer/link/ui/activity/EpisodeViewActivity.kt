package com.mediaPlayer.link.ui.activity

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.mediaPlayer.link.R
import com.mediaPlayer.link.core.BaseActivity
import com.mediaPlayer.link.databinding.ActivityEpisodeViewBinding
import com.mediaPlayer.link.model.netwokModels.Episode
import com.mediaPlayer.link.model.netwokModels.WebSeriesFeed
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.utils.Constants
import com.mediaPlayer.link.utils.SessionManager
import com.mediaPlayer.link.utils.Utils
import com.squareup.picasso.Picasso

class EpisodeViewActivity :
    BaseActivity<MainViewModel, ActivityEpisodeViewBinding>(MainViewModel::class.java) {
    private lateinit var viewModel: MainViewModel


    override fun getBinding(): ActivityEpisodeViewBinding {
        return ActivityEpisodeViewBinding.inflate(layoutInflater)
    }

    override fun initViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    override fun onCreateBase() {
        mBinding?.imgBack?.setOnClickListener {
            finish()
        }



        if (intent.getSerializableExtra(Constants.EXTRA_WEB_EPISODE_DATA) != null && intent.getSerializableExtra(Constants.EXTRA_WEB_SERIES_DATA) != null) {
            val episode = intent.getSerializableExtra(Constants.EXTRA_WEB_EPISODE_DATA) as Episode
            val webSeriesFeed =
                intent.getSerializableExtra(Constants.EXTRA_WEB_SERIES_DATA) as WebSeriesFeed

            setUi(episode, webSeriesFeed)

        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUi(episode: Episode, webSeriesFeed: WebSeriesFeed) {
        with(mBinding!!) {
            txtToolMovieName.text = webSeriesFeed.movieName.toString()


            with(mBinding!!) {


                mBinding?.imgShare?.setOnClickListener {
                    val bitmap = imageMoviePoster.drawingCache
                    Utils.shareImageAndText(
                        bitmap, this@EpisodeViewActivity, sessionManager.getString(SessionManager.UPDATEURI)
                            .toString()
                    )
                }


                buttonPlay.setOnClickListener {
                    Utils.openVideoPlayer(this@EpisodeViewActivity, episode.videoUrl.toString())
                }



                Glide.with(this@EpisodeViewActivity)
                    .load(webSeriesFeed.poster)
                    .apply(Utils.getGlideRequest(this@EpisodeViewActivity))
                    .error(
                        Picasso.get()
                            .load(webSeriesFeed.poster)
                            .error(R.drawable.ic_not_found)
                            .into(imageMoviePoster)
                    )
                    .into(imageMoviePoster)


                txtEpisodeNumber.text = episode.episod.toString()

                textMovieTitle.text = webSeriesFeed.movieName.toString()
                ratingBar.rating = (webSeriesFeed.rating?.toFloat()!! / 2)
                viewMovieRating.text = "Rating ${(webSeriesFeed.rating?.toDouble()!! / 2)}"
                txtLanguage.text = webSeriesFeed.language.toString()
                txtGenres.text = webSeriesFeed.genre.toString()
                txtDuration.text = webSeriesFeed.duration.toString()


                with(mBinding!!) {
                    Glide.with(this@EpisodeViewActivity)
                        .load(webSeriesFeed.screenshot)
                        .apply(Utils.getGlideRequest(this@EpisodeViewActivity))
                        .error(
                            Picasso.get()
                                .load(webSeriesFeed.screenshot)
                                .error(R.drawable.ic_not_found)
                                .into(imgScreenShot)
                        )
                        .into(imgScreenShot)
                }

            }

        }
    }

    override fun observe() {
    }
}