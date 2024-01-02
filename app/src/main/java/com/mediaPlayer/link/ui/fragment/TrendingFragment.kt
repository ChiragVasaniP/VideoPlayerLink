package com.mediaPlayer.link.ui.fragment

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mediaPlayer.link.R
import com.mediaPlayer.link.adapter.TrendingPosterViewPagerAdapter
import com.mediaPlayer.link.core.BaseFragment
import com.mediaPlayer.link.databinding.FragmentTrendingBinding
import com.mediaPlayer.link.model.netwokModels.CategoryWiseMovieModel
import com.mediaPlayer.link.model.netwokModels.MovieFeed
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.ui.activity.SearchMovieActivity
import com.mediaPlayer.link.utils.DialogHelper
import com.mediaPlayer.link.utils.SessionManager
import com.mediaPlayer.link.utils.Utils
import com.squareup.picasso.Picasso


class TrendingFragment :
    BaseFragment<MainViewModel, FragmentTrendingBinding>(MainViewModel::class.java) {
    private var posterList: ArrayList<MovieFeed> = arrayListOf()
    lateinit var adapter: TrendingPosterViewPagerAdapter
    private var currentPage = 1
    private var totalPage = 0

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup): FragmentTrendingBinding {
        return FragmentTrendingBinding.inflate(inflater, container, false)
    }

    override fun getViewModelClass(): MainViewModel {
        return ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun initialize() {
        if (mViewModel.trendingList.value == null) {
            adapter = TrendingPosterViewPagerAdapter(posterList, requireActivity())
            mBinding.rvTrending.adapter = adapter
            mViewModel.setTrendingCategory(currentPage)
        }
    }


    override fun viewCreated() {
        with(mBinding) {

            search.setOnClickListener {
                startActivity(Intent(requireActivity(), SearchMovieActivity::class.java))
            }


            iclNoNetworkFound.btnRefresh.setOnClickListener {
                mViewModel.setTrendingCategory(currentPage)
            }


            mBinding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    currentPage++
                    if (currentPage <= totalPage) {
                        mViewModel.setTrendingCategory(currentPage)
                    }
                }
            })

        }

        mBinding.telegram.setOnClickListener {
//            Utils.intentMessageTelegram(requireActivity(),sessionManager.getString(SessionManager.TELEGRAMLINK))
            try {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(sessionManager.getString(SessionManager.TELEGRAMLINK)))
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("TAG_null", "viewCreated:${e.message} ")
            }

        }

    }


    override fun observer() {
        with(mViewModel) {
            trendingList.observe(requireActivity()) { commonResponse ->
                if (isVisible) {
                    if (commonResponse != null) {
                        if (commonResponse.code in 199..299) {
                            mBinding.topTrending.isVisible = true
                            mBinding.iclNoNetworkFound.root.isVisible = false
                            val categoryWiseMovieModel =
                                gson.fromJson(commonResponse.apiResponse, CategoryWiseMovieModel::class.java)
                            if (categoryWiseMovieModel != null) {
                                totalPage =
                                    categoryWiseMovieModel.data!!.pagination?.lastPage.toString()
                                        .toInt()
                                if (categoryWiseMovieModel.data?.feed?.get(0)?.image?.get(0)?.imageUrl != null) {
                                    Glide.with(requireActivity())
                                        .load(categoryWiseMovieModel.data?.feed?.get(0)?.image?.get(0)?.imageUrl)
                                        .error(
                                            Picasso.get()
                                                .load(categoryWiseMovieModel.data?.feed?.get(0)?.image?.get(0)?.imageUrl)
                                                .error(R.drawable.ic_not_found)
                                                .into(mBinding.imgTrending)
                                        )
                                        .apply(Utils.getGlideRequest(requireActivity()))
                                        .into(mBinding.imgTrending)
                                }
                                if (categoryWiseMovieModel.data != null) {
                                    posterList.addAll(categoryWiseMovieModel.data!!.feed)
                                    adapter.setNewData(posterList)
//                                    mBinding.rvTrending.adapter = TrendingPosterViewPagerAdapter(categoryWiseMovieModel.data!!.feed, requireActivity())
                                }
                            }
                        } else {
                            DialogHelper.showServerError(requireActivity())
                            trendingList.value = null
                            if (posterList.isEmpty()) {
                                mBinding.topTrending.isVisible = false
                                mBinding.iclNoNetworkFound.root.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }
}