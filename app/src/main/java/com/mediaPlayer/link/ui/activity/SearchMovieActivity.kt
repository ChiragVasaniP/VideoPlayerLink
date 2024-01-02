package com.mediaPlayer.link.ui.activity

import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.mediaPlayer.link.adapter.SearchMovieAdapter
import com.mediaPlayer.link.core.BaseActivity
import com.mediaPlayer.link.databinding.ActivitySearchMovieBinding
import com.mediaPlayer.link.model.netwokModels.CategoryWiseMovieModel
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.utils.DialogHelper

class SearchMovieActivity :
    BaseActivity<MainViewModel, ActivitySearchMovieBinding>(MainViewModel::class.java) {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: SearchMovieAdapter

    override fun getBinding(): ActivitySearchMovieBinding {
        return ActivitySearchMovieBinding.inflate(layoutInflater)
    }

    override fun initViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    override fun onCreateBase() {
        viewModel.setSearchMovie()
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
            searchMovieList.observe(this@SearchMovieActivity) { commonResponse ->
                if (commonResponse != null) {
                    if (commonResponse.code in 199..299) {
                        val model =
                            gson.fromJson(commonResponse.apiResponse, CategoryWiseMovieModel::class.java)
                        if (model.data != null) {

                            adapter =
                                SearchMovieAdapter(model.data!!.feed, this@SearchMovieActivity)
                            mBinding?.rvSearchMovieResult?.adapter = adapter
                        }
                    } else {
                        DialogHelper.showServerError(this@SearchMovieActivity)
                        southMovieList.value = null
                    }
                }
            }
        }
    }
}