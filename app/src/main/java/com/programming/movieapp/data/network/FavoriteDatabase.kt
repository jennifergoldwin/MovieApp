package com.programming.movieapp.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.programming.movieapp.data.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase: RoomDatabase() {

    abstract fun daoInterface(): TmdbDao
}