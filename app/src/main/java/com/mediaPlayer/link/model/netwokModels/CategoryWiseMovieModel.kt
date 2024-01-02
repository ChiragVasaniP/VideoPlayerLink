package com.mediaPlayer.link.model.netwokModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryWiseMovieModel(
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: MovieData? = MovieData()

) : Serializable

data class MoviePagination(

    @SerializedName("current_page") var currentPage: String? = null,
    @SerializedName("last_page") var lastPage: String? = null,
    @SerializedName("per_page") var perPage: String? = null,
    @SerializedName("total") var total: String? = null,
    @SerializedName("path") var path: String? = null

) : Serializable

data class MovieImage(

    @SerializedName("image_path") var imagePath: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null

) : Serializable

data class MovieCategory(

    @SerializedName("name") var name: String? = null

) : Serializable

data class MovieVideo(

    @SerializedName("video_path") var videoPath: String? = null,
    @SerializedName("quality") var quality: String? = null

) : Serializable


data class MovieFeed(
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
    @SerializedName("image") var image: ArrayList<MovieImage> = arrayListOf(),
    @SerializedName("category") var category: ArrayList<MovieCategory> = arrayListOf(),
    @SerializedName("video") var video: ArrayList<MovieVideo> = arrayListOf()

) : Serializable


data class MovieData(

    @SerializedName("pagination") var pagination: MoviePagination? = MoviePagination(),
    @SerializedName("feed") var feed: ArrayList<MovieFeed> = arrayListOf()

) : Serializable