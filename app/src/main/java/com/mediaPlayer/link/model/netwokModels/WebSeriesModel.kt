package com.mediaPlayer.link.model.netwokModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WebSeriesModel(

    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: WebSeriesData? = WebSeriesData()

) : Serializable


data class WebSeriesData(

    @SerializedName("pagination") var pagination: WebSeriesPagination? = WebSeriesPagination(),
    @SerializedName("feed") var feed: ArrayList<WebSeriesFeed> = arrayListOf()

) : Serializable

data class WebSeriesPagination(

    @SerializedName("current_page") var currentPage: String? = null,
    @SerializedName("last_page") var lastPage: String? = null,
    @SerializedName("per_page") var perPage: String? = null,
    @SerializedName("total") var total: String? = null,
    @SerializedName("path") var path: String? = null

) : Serializable

data class WebSeriesFeed(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("movie_name") var movieName: String? = null,
    @SerializedName("movie_slug") var movieSlug: String? = null,
    @SerializedName("genre") var genre: String? = null,
    @SerializedName("rating") var rating: String? = null,
    @SerializedName("duration") var duration: String? = null,
    @SerializedName("screenshot") var screenshot: String? = null,
    @SerializedName("poster") var poster: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("sub_category_name") var subCategoryName: String? = null,
    @SerializedName("sessions") var sessions: ArrayList<Sessions> = arrayListOf()

) : Serializable

data class Sessions(

    @SerializedName("sessionnumber") var sessionnumber: String? = null,
    @SerializedName("session_img") var sessionImg: String? = null,
    @SerializedName("episode") var episode: ArrayList<Episode> = arrayListOf()

) : Serializable


data class Episode(

    @SerializedName("episod") var episod: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("video_url") var videoUrl: String? = null,
    @SerializedName("movie_id") var movieId: Int? = null,
    @SerializedName("season_id") var seasonId: Int? = null,
    @SerializedName("episode_id") var episodeId: Int? = null

) : Serializable



