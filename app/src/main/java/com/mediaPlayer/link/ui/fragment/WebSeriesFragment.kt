package com.mediaPlayer.link.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.mediaPlayer.link.adapter.webseries.WebSeriesAdapter
import com.mediaPlayer.link.core.BaseFragment
import com.mediaPlayer.link.databinding.FragmentWebSeriesBinding
import com.mediaPlayer.link.model.netwokModels.WebSeriesFeed
import com.mediaPlayer.link.model.netwokModels.WebSeriesModel
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.ui.activity.SearchWebSeriesActivity
import com.mediaPlayer.link.utils.DialogHelper


class WebSeriesFragment :
    BaseFragment<MainViewModel, FragmentWebSeriesBinding>(MainViewModel::class.java) {

    lateinit var adapter: WebSeriesAdapter
    private var posterList: ArrayList<WebSeriesFeed>? = null
    private var currentPage = 1
    private var totalPage = 0


    override fun getBinding(inflater: LayoutInflater, container: ViewGroup): FragmentWebSeriesBinding {
        return FragmentWebSeriesBinding.inflate(inflater, container, false)
    }

    override fun getViewModelClass(): MainViewModel {
        return ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun initialize() {
        posterList = arrayListOf()
        adapter = WebSeriesAdapter()


        if (mViewModel.webSeriesList.value == null) {
            mViewModel.setWebSeriesCategory(currentPage)
        }
    }


    override fun viewCreated() {

        adapter = WebSeriesAdapter(posterList, requireActivity())
        mBinding.rvWebSeries.adapter = adapter
        mBinding.rvWebSeries.setHasFixedSize(true)

        mBinding.iclNoNetworkFound.btnRefresh.setOnClickListener {
            mViewModel.setWebSeriesCategory(currentPage)
        }

        mBinding.search.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchWebSeriesActivity::class.java))
        }

        mBinding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                currentPage++
                if (currentPage <= totalPage) {
                    mViewModel.setWebSeriesCategory(currentPage)
                }
            }
        })
    }


    override fun observer() {
        with(mViewModel) {
            webSeriesList.observe(requireActivity()) { commonResponse ->
                if (isVisible) {
                    if (commonResponse != null) {
                        if (commonResponse.code in 199..299) {
                            mBinding.llWebSeries.isVisible = true
                            mBinding.iclNoNetworkFound.root.isVisible = false
                            val model =
                                gson.fromJson(commonResponse.apiResponse, WebSeriesModel::class.java)
                            if (model.data != null) {
                                totalPage = model.data!!.pagination?.lastPage.toString().toInt()
                                posterList?.addAll(model.data!!.feed)
                                adapter.setNewData(posterList)
                            }
                        } else {
                            DialogHelper.showServerError(requireActivity())
                            webSeriesList.value = null
                            if (posterList.isNullOrEmpty()) {
                                mBinding.llWebSeries.isVisible = false
                                mBinding.iclNoNetworkFound.root.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }
}