package com.mediaPlayer.link.ui.activity

import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.mediaPlayer.link.adapter.SearchWebSeriesAdapter
import com.mediaPlayer.link.core.BaseActivity
import com.mediaPlayer.link.databinding.ActivitySearchWebSeriesBinding
import com.mediaPlayer.link.model.netwokModels.WebSeriesModel
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.utils.DialogHelper

class SearchWebSeriesActivity :
    BaseActivity<MainViewModel, ActivitySearchWebSeriesBinding>(MainViewModel::class.java) {
    lateinit var adapter: SearchWebSeriesAdapter
    lateinit var viewModel: MainViewModel

    override fun getBinding(): ActivitySearchWebSeriesBinding {
        return ActivitySearchWebSeriesBinding.inflate(layoutInflater)
    }

    override fun initViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
        viewModel.searchWebSeries()
    }

    override fun onCreateBase() {

        viewModel.searchWebSeries()
        showSearchMessageWhenEmpty()

        with(mBinding!!) {
            edtSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    showSearchMessageWhenEmpty()
                }

                override fun afterTextChanged(s: Editable?) {
                    adapter.filter.filter(s.toString())
                }

            })


        }

    }

    fun showSearchMessageWhenEmpty() {
        with(mBinding!!) {
            if (edtSearch.text.isEmpty()) {
                searchMessage.text = "Search Result Show Here"
                searchMessage.isVisible = true
                rvSearchMovieResult.isVisible = false
            } else {
                searchMessage.isVisible = false
                rvSearchMovieResult.isVisible = true
                if (adapter.getmobielistSize()?.isEmpty() == true) {
                    searchMessage.isVisible = true
                    rvSearchMovieResult.isVisible = false
                    searchMessage.text = "Movie Not Found"

                }
            }
        }
    }

    override fun observe() {
        with(viewModel) {
            searchWebSeries.observe(this@SearchWebSeriesActivity) { commonResponse ->
                if (commonResponse != null) {
                    if (commonResponse.code in 199..299) {
                        val model =
                            gson.fromJson(commonResponse.apiResponse, WebSeriesModel::class.java)
                        if (model.data != null) {
                            adapter =
                                SearchWebSeriesAdapter(model.data!!.feed, this@SearchWebSeriesActivity)
                            mBinding?.rvSearchMovieResult?.adapter = adapter
                        }
                    } else {
                        DialogHelper.showServerError(this@SearchWebSeriesActivity)
                        webSeriesList.value = null
                    }
                }

            }
        }
    }


}