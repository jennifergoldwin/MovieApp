package com.programming.movieapp.data.models

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("id") val id: Long,
    @SerializedName("original_title") val title: String,
    @SerializedName("poster_path") val posterUrl: String,
    @SerializedName("overview") val synopsis: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("videos") val videos: VideoResponse,
)