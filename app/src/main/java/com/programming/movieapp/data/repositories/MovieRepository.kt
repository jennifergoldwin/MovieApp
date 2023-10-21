package com.programming.movieapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.programming.movieapp.BuildConfig
import com.programming.movieapp.data.models.Movie
import com.programming.movieapp.data.models.MovieDetail
import com.programming.movieapp.data.network.TmdbDao
import com.programming.movieapp.data.network.TmdbService
import com.programming.movieapp.data.paging.FavMoviePagingSource
import com.programming.movieapp.data.paging.MoviePagingSource
import com.programming.movieapp.utils.QueryType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.*

@Singleton
class MovieRepository @Inject constructor(private val tmdbService: TmdbService, private val tmdbDao: TmdbDao) {

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviePagingSource(tmdbService, BuildConfig.TMDB_API_KEY,QueryType.POPULAR,"") }
        ).flow
    }

    fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviePagingSource(tmdbService, BuildConfig.TMDB_API_KEY,QueryType.UPCOMING,"") }
        ).flow
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviePagingSource(tmdbService, BuildConfig.TMDB_API_KEY,QueryType.NOWPLAYING,"") }
        ).flow
    }

    fun getMovieDetail(movieId:Int) : Single<MovieDetail> {
        return tmdbService.getMovieDetail(movieId,BuildConfig.TMDB_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun searchMovie(query: String) : Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviePagingSource(tmdbService, BuildConfig.TMDB_API_KEY,QueryType.SEARCH,query) }
        ).flow
    }

    //dao
    fun isMovieInDao(movieId: Int) : Single<Movie> {
        return tmdbDao.isMovieExists(movieId = movieId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    suspend fun addMovieToFavorites(movie: Movie) {
        withContext(Dispatchers.IO) {
            tmdbDao.insertMovie(movie)
        }
    }

    suspend fun deleteMovieFromFavorites(movie: Movie) {
        withContext(Dispatchers.IO) {
            tmdbDao.deleteMovie(movie)
        }
    }

    fun getFavMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 1,enablePlaceholders = false,prefetchDistance = 10,),
            pagingSourceFactory = { FavMoviePagingSource(tmdbDao,"") }
        ).flow
    }

    fun searchMovieInDAO(query: String) : Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { FavMoviePagingSource(tmdbDao,query) }
        ).flow
    }
}
