package com.mediaPlayer.link.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.mediaPlayer.link.adapter.SouthMoviePosterAdapter
import com.mediaPlayer.link.core.BaseFragment
import com.mediaPlayer.link.databinding.FragmentSouthMoveBinding
import com.mediaPlayer.link.model.netwokModels.CategoryWiseMovieModel
import com.mediaPlayer.link.model.netwokModels.MovieFeed
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.ui.activity.SearchMovieActivity
import com.mediaPlayer.link.utils.DialogHelper


class SouthMovieFragment :
    BaseFragment<MainViewModel, FragmentSouthMoveBinding>(MainViewModel::class.java) {

    lateinit var adapter: SouthMoviePosterAdapter
    private var posterList: ArrayList<MovieFeed>? = null
    private var currentPage = 1
    private var totalPage = 0


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup): FragmentSouthMoveBinding {
        return FragmentSouthMoveBinding.inflate(inflater, container, false)
    }

    override fun getViewModelClass(): MainViewModel {
        return ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun initialize() {
        posterList = arrayListOf()
        adapter = SouthMoviePosterAdapter()

        if (mViewModel.southMovieList.value == null) {
            mViewModel.setSouthMovieCategory(currentPage)
        }
    }


    override fun viewCreated() {

        adapter = SouthMoviePosterAdapter(posterList, requireActivity())
        mBinding.rvSMovie.adapter = adapter
        mBinding.rvSMovie.setHasFixedSize(true)


        mBinding.search.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchMovieActivity::class.java))
        }

        mBinding.iclNoNetworkFound.btnRefresh.setOnClickListener {
            mViewModel.setSouthMovieCategory(currentPage)
        }

        mBinding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                currentPage++
                if (currentPage <= totalPage) {
                    mViewModel.setSouthMovieCategory(currentPage)
                }
            }
        })
    }


    override fun observer() {
        with(mViewModel) {
            southMovieList.observe(requireActivity()) { commonResponse ->
                if (isVisible) {
                    if (commonResponse != null) {
                        if (commonResponse.code in 199..299) {
                            mBinding.llSouthMovie.isVisible = true
                            mBinding.iclNoNetworkFound.root.isVisible = false
                            val model =
                                gson.fromJson(commonResponse.apiResponse, CategoryWiseMovieModel::class.java)
                            if (model.data != null) {

                                totalPage = model.data!!.pagination?.lastPage.toString().toInt()
                                posterList?.addAll(model.data!!.feed)
                                adapter.setNewData(posterList)
                            }

                        } else {
                            DialogHelper.showServerError(requireActivity())
                            southMovieList.value = null
                            if (posterList.isNullOrEmpty()) {
                                mBinding.llSouthMovie.isVisible = false
                                mBinding.iclNoNetworkFound.root.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }


}