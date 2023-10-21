package com.programming.movieapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.programming.movieapp.data.models.Movie
import com.programming.movieapp.data.network.TmdbDao

class FavMoviePagingSource(
    private val tmdbDao: TmdbDao, private val query:String
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 0

        return try {
            val entities = if (query.isEmpty()){
                tmdbDao.getFavorite()
            }else{
                tmdbDao.findMoviesByTitle(query)
            }
            Log.e("ENTITIES",entities.toString())

            // simulate page loading
//            if (page != 0) delay(1000)

            LoadResult.Page(
                data = entities,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }


}