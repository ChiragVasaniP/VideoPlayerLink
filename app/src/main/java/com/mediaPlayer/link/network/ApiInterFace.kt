package com.mediaPlayer.link.network

import com.google.gson.JsonObject
import com.mediaPlayer.link.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterFace {

    @GET(Constants.MOVIE)
    fun categoryWisePaginationMovieRecord(
        @Query(Constants.CATEGORY_ID) CATEGORY_ID: String,
        @Query(Constants.PER_PAGE) PER_PAGE: String,
        @Query(Constants.CURRENT_PAGE) CURRENT_PAGE: Int,
    ): Call<JsonObject>


    @GET(Constants.MOVIE)
    fun searchAllMovie(
        @Query(Constants.PER_PAGE) PER_PAGE: String,
    ): Call<JsonObject>


    @GET(Constants.WEB_SERIES_SEARCH)
    fun searchWebSeries(
        @Query(Constants.PER_PAGE) PER_PAGE: String,
    ): Call<JsonObject>


    @GET(Constants.WEB_SERIES)
    fun webSeries(
        @Query(Constants.PER_PAGE) PER_PAGE: String,
        @Query(Constants.CURRENT_PAGE) CURRENT_PAGE: Int,
    ): Call<JsonObject>
}