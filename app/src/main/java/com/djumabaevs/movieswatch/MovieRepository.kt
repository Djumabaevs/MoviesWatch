package com.djumabaevs.movieswatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.djumabaevs.movieswatch.api.MovieService
import com.djumabaevs.movieswatch.model.Movie

class MovieRepository(private val movieService: MovieService) {

    private val movieLiveData = MutableLiveData<List<Movie>>()
    private val errorLiveData = MutableLiveData<String>()

    val movies:LiveData<List<Movie>>
    get() = movieLiveData

    val error:LiveData<String>
    get() = errorLiveData

    private val apiKey = "89691d3075f96e7c7e086344e47cfb18"
//    fun fetchMovies() = movieService.getPopularMovies(apiKey)
    suspend fun fetchMovies() {
        try {
            val popularMovies = movieService.getPopularMovies(apiKey)
            movieLiveData.postValue(popularMovies.results)
        } catch (e: Exception) {
            errorLiveData.postValue("Error occured: ${e.message}")
        }
    }
}