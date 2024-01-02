package com.mediaPlayer.link.model.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.mediaPlayer.link.core.BaseViewModel
import com.mediaPlayer.link.core.CommonResponseModel
import com.mediaPlayer.link.network.RetrofitClient
import com.mediaPlayer.link.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(App: Application) : BaseViewModel(App) {

    val bollywoodList: MutableLiveData<CommonResponseModel> by lazy { MutableLiveData<CommonResponseModel>() }
    val trendingList: MutableLiveData<CommonResponseModel> by lazy { MutableLiveData<CommonResponseModel>() }
    val hollywoodList: MutableLiveData<CommonResponseModel> by lazy { MutableLiveData<CommonResponseModel>() }
    val southMovieList: MutableLiveData<CommonResponseModel> by lazy { MutableLiveData<CommonResponseModel>() }
    val webSeriesList: MutableLiveData<CommonResponseModel> by lazy { MutableLiveData<CommonResponseModel>() }
    val searchMovieList: MutableLiveData<CommonResponseModel> by lazy { MutableLiveData<CommonResponseModel>() }
    val searchWebSeries: MutableLiveData<CommonResponseModel> by lazy { MutableLiveData<CommonResponseModel>() }

    fun setTrendingCategory(currentPage: Int) {
        isProgress.value = true
        val call =
            RetrofitClient.instance?.myApi?.categoryWisePaginationMovieRecord(Constants.CATEGORY_ID_1, Constants.PER_PAGE_DATA, currentPage)
        call?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    trendingList.value = CommonResponseModel(response.body(), response.code())
                } else {
                    trendingList.value = CommonResponseModel(null, 500)
                }
                isProgress.value = false
            }

            override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                trendingList.value = CommonResponseModel(null, 500)
                isProgress.value = false
                call.cancel()
            }
        })
    }

    fun setHollywoodCategory(currentPage: Int) {
        isProgress.value = true
        val call =
            RetrofitClient.instance?.myApi?.categoryWisePaginationMovieRecord(Constants.CATEGORY_ID_2, Constants.PER_PAGE_DATA, currentPage)
        call?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    hollywoodList.value = CommonResponseModel(response.body(), response.code())
                } else {
                    hollywoodList.value = CommonResponseModel(null, 500)
                }
                isProgress.value = false
            }

            override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                hollywoodList.value = CommonResponseModel(null, 500)
                isProgress.value = false
                call.cancel()

            }

        })
    }

    fun setBollywoodCategory(currentPage: Int) {
        isProgress.value = true
        val call =
            RetrofitClient.instance?.myApi?.categoryWisePaginationMovieRecord(Constants.CATEGORY_ID_3, Constants.PER_PAGE_DATA, currentPage)
        call?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    bollywoodList.value = CommonResponseModel(response.body(), response.code())
                } else {
                    bollywoodList.value = CommonResponseModel(null, 500)
                }
                isProgress.value = false
            }

            override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                bollywoodList.value = CommonResponseModel(null, 500)
                isProgress.value = false
                call.cancel()

            }
        })
    }

    fun setSouthMovieCategory(currentPage: Int) {
        isProgress.value = true
        val call =
            RetrofitClient.instance?.myApi?.categoryWisePaginationMovieRecord(Constants.CATEGORY_ID_4, Constants.PER_PAGE_DATA, currentPage)
        call?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    southMovieList.value = CommonResponseModel(response.body(), response.code())
                } else {
                    southMovieList.value = CommonResponseModel(null, 500)
                }
                isProgress.value = false
            }

            override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                southMovieList.value = CommonResponseModel(null, 500)
                isProgress.value = false
                call.cancel()

            }

        })
    }

    fun setWebSeriesCategory(currentPage: Int) {
        isProgress.value = true
        val call = RetrofitClient.instance?.myApi?.webSeries(Constants.PER_PAGE_DATA, currentPage)
        call?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    webSeriesList.value = CommonResponseModel(response.body(), response.code())
                } else {
                    webSeriesList.value = CommonResponseModel(null, 500)
                }
                isProgress.value = false
            }

            override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                webSeriesList.value = CommonResponseModel(null, 500)
                isProgress.value = false
                call.cancel()
            }
        })
    }

    fun setSearchMovie() {
        isProgress.value = true
        val call =
            RetrofitClient.instance?.myApi?.searchAllMovie(Constants.PER_PAGE_SEARCH)
        call?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    searchMovieList.value = CommonResponseModel(response.body(), response.code())
                } else {
                    searchMovieList.value = CommonResponseModel(null, 500)
                }
                isProgress.value = false
            }

            override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                searchMovieList.value = CommonResponseModel(null, 500)
                isProgress.value = false
                call.cancel()

            }

        })
    }

    fun searchWebSeries() {
        isProgress.value = true
        val call =
            RetrofitClient.instance?.myApi?.searchWebSeries(Constants.PER_PAGE_SEARCH)
        call?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    searchWebSeries.value = CommonResponseModel(response.body(), response.code())
                } else {
                    searchWebSeries.value = CommonResponseModel(null, 500)
                }
                isProgress.value = false
            }

            override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                searchWebSeries.value = CommonResponseModel(null, 500)
                isProgress.value = false
                call.cancel()

            }

        })
    }
}