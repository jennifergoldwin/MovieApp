package com.programming.movieapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.programming.movieapp.data.models.Movie
import com.programming.movieapp.data.models.MovieDetail
import com.programming.movieapp.data.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val popularMovies: Flow<PagingData<Movie>> = repository.getPopularMovies()
        .cachedIn(viewModelScope)

    val upcomingMovies: Flow<PagingData<Movie>> = repository.getUpcomingMovies()
        .cachedIn(viewModelScope)

    val nowPlayingMovies: Flow<PagingData<Movie>> = repository.getNowPlayingMovies()
        .cachedIn(viewModelScope)

    private val _searchedMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchedMovies: Flow<PagingData<Movie>> get() = _searchedMovies

    private val _selectedMovie = MutableLiveData<MovieDetail>()
    val selectedMovie: LiveData<MovieDetail> get() = _selectedMovie

    //DAO
    private val _favMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val favMovies: Flow<PagingData<Movie>> get() = _favMovies //SHOW LIST FAV

    private val _moviesExistsInFav = MutableLiveData<Movie?>()
    val movieExistsInFav: LiveData<Movie?> get() = _moviesExistsInFav //IS MOVIE ADDED TO FAV

    private val _searchedMoviesDAO = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchedMoviesDAO: Flow<PagingData<Movie>> get() = _searchedMoviesDAO


    fun getPosterUrl(posterPath: String?): String? {
        return if (!posterPath.isNullOrBlank()) {
            "https://image.tmdb.org/t/p/w500$posterPath" // Adjust the size as needed
        } else {
            null
        }
    }

    fun getMovieDetail(movieId: Int){
        Log.e("detail","MSK")
        repository.getMovieDetail(movieId = movieId).subscribe(
            {
                _selectedMovie.postValue(it)
            },
            {
                Log.e("error", it.message.toString())
            }
        )
    }

    fun searchMovies(query : String) {
        val flow = repository.searchMovie(query)
            .cachedIn(viewModelScope)
        flow.onEach {
            _searchedMovies.value = it
        }.launchIn(viewModelScope)
    }


    //DAO
    fun isMovieInDao(movieId: Int){
        repository.isMovieInDao(movieId).subscribe(
            {
                _moviesExistsInFav.postValue(it)
            },{
                Log.e("error", it.message.toString())
                _moviesExistsInFav.postValue(null)
            }
        )
    }

    suspend fun addToFav(movie: Movie){
        repository.addMovieToFavorites(movie)
    }

    suspend fun deleteFromFav(movie: Movie){
        repository.deleteMovieFromFavorites(movie)
    }

    fun refreshFavMovies() {
        val flow = repository.getFavMovies()
            .cachedIn(viewModelScope)
        flow.onEach {
            _favMovies.value = it
        }.launchIn(viewModelScope)
    }

    fun searchMoviesInDAO(query : String) {
        val flow = repository.searchMovieInDAO(query)
            .cachedIn(viewModelScope)
        flow.onEach {
            _searchedMoviesDAO.value = it
        }.launchIn(viewModelScope)
    }

}
