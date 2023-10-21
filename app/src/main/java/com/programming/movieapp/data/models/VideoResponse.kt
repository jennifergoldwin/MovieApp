package com.programming.movieapp.data.models

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("results") val results: List<Video>
)