package com.mediaPlayer.link.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.mediaPlayer.link.adapter.HollywoodPosterAdapter
import com.mediaPlayer.link.core.BaseFragment
import com.mediaPlayer.link.databinding.FragmentHollywoodBinding
import com.mediaPlayer.link.model.netwokModels.CategoryWiseMovieModel
import com.mediaPlayer.link.model.netwokModels.MovieFeed
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.ui.activity.SearchMovieActivity
import com.mediaPlayer.link.utils.DialogHelper


class HollywoodFragment :
    BaseFragment<MainViewModel, FragmentHollywoodBinding>(MainViewModel::class.java) {

    lateinit var adapter: HollywoodPosterAdapter
    private var posterList: ArrayList<MovieFeed>? = null
    private var currentPage = 1
    private var totalPage = 0


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup): FragmentHollywoodBinding {
        return FragmentHollywoodBinding.inflate(inflater, container, false)
    }

    override fun getViewModelClass(): MainViewModel {
        return ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun initialize() {
        posterList = arrayListOf()
        adapter = HollywoodPosterAdapter()
        if (mViewModel.hollywoodList.value == null) {
            mViewModel.setHollywoodCategory(currentPage)
        }
    }


    override fun viewCreated() {

        mBinding.iclNoNetworkFound.btnRefresh.setOnClickListener {
            mViewModel.setHollywoodCategory(currentPage)
        }

        mBinding.search.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchMovieActivity::class.java))
        }

        adapter = HollywoodPosterAdapter(posterList, requireActivity())
        mBinding.rvHollywood.adapter = adapter
        mBinding.rvHollywood.setHasFixedSize(true)

        mBinding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                currentPage++
                if (currentPage <= totalPage) {
                    mViewModel.setHollywoodCategory(currentPage)
                }
            }
        })
    }


    override fun observer() {
        with(mViewModel) {
            hollywoodList.observe(requireActivity()) { commonResponse ->
                if (isVisible) {
                    if (commonResponse != null) {
                        mBinding.llHollywood.isVisible = true
                        mBinding.iclNoNetworkFound.root.isVisible = false
                        if (commonResponse.code in 199..299) {
                            val model =
                                gson.fromJson(commonResponse.apiResponse, CategoryWiseMovieModel::class.java)
                            if (model.data != null) {
                                totalPage = model.data!!.pagination?.lastPage.toString().toInt()
                                posterList?.addAll(model.data!!.feed)
                                adapter.setNewData(posterList)
                            }
                        } else {
                            DialogHelper.showServerError(requireActivity())
                            hollywoodList.value = null
                            if (posterList.isNullOrEmpty()) {
                                mBinding.llHollywood.isVisible = false
                                mBinding.iclNoNetworkFound.root.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }


}