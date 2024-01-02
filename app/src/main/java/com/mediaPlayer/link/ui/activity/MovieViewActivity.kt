package com.mediaPlayer.link.ui.activity

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.mediaPlayer.link.R
import com.mediaPlayer.link.adapter.QualityAdapter
import com.mediaPlayer.link.core.BaseActivity
import com.mediaPlayer.link.databinding.ActivityMovieViewBinding
import com.mediaPlayer.link.model.netwokModels.MovieFeed
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.utils.Constants
import com.mediaPlayer.link.utils.SessionManager
import com.mediaPlayer.link.utils.Utils
import com.squareup.picasso.Picasso


class MovieViewActivity :
    BaseActivity<MainViewModel, ActivityMovieViewBinding>(MainViewModel::class.java) {
    private lateinit var viewModel: MainViewModel


    override fun getBinding(): ActivityMovieViewBinding {
        return ActivityMovieViewBinding.inflate(layoutInflater)
    }

    override fun initViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    override fun onCreateBase() {
        showQurekaAds(this@MovieViewActivity)
        mBinding?.imgBack?.setOnClickListener {
            finish()
        }





        if (intent.getSerializableExtra(Constants.EXTRA_MOVIE_DATA) != null) {
            val movieModel = intent.getSerializableExtra(Constants.EXTRA_MOVIE_DATA) as? MovieFeed
            if (movieModel != null) {
                setUi(movieModel)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUi(movieModel: MovieFeed) {
        with(mBinding!!) {
            imageMoviePoster.isDrawingCacheEnabled = true

            mBinding?.imgShare?.setOnClickListener {
                val bitmap = imageMoviePoster.drawingCache
                Utils.shareImageAndText(
                    bitmap, this@MovieViewActivity, sessionManager.getString(SessionManager.UPDATEURI)
                        .toString()
                )
            }



            txtToolMovieName.text = movieModel.movieName


            Glide.with(this@MovieViewActivity)
                .load(movieModel.poster)
                .apply(Utils.getGlideRequest(this@MovieViewActivity))
                .error(
                    Picasso.get()
                        .load(movieModel.poster)
                        .error(R.drawable.ic_not_found)
                        .into(imageMoviePoster)
                )
                .into(imageMoviePoster)


            textMovieTitle.text = movieModel.movieName
            ratingBar.rating = (movieModel.rating?.toFloat()!! / 2)
            viewMovieRating.text = "Rating ${(movieModel.rating?.toDouble()!! / 2)}"
            txtLanguage.text = movieModel.language.toString()
            txtGenres.text = movieModel.genre.toString()
            txtDuration.text = movieModel.duration.toString()

            Glide.with(this@MovieViewActivity)
                .load(movieModel.screenshot)
                .error(
                    Picasso.get()
                        .load(movieModel.screenshot)
                        .error(R.drawable.ic_not_found)
                        .into(imgScreenShot)
                )
                .apply(Utils.getGlideRequest(this@MovieViewActivity))
                .into(imgScreenShot)


            with(rvMovieQuality) {
                adapter = QualityAdapter(this@MovieViewActivity, movieModel.video)
                setHasFixedSize(true)
            }

        }
    }


    override fun observe() {

    }


}