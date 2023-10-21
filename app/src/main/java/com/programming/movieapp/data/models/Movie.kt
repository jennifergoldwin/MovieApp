package com.programming.movieapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Favorite")
data class Movie(
    @SerializedName("id") @PrimaryKey @ColumnInfo(name = "id")
    val id: Long,
    @SerializedName("title") @ColumnInfo(name = "title")
    val title: String,
    @SerializedName("poster_path") @ColumnInfo(name = "poster_path")
    val posterUrl: String,
)
