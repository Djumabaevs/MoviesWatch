package com.djumabaevs.movieswatch

import androidx.lifecycle.MutableLiveData
import com.djumabaevs.movieswatch.api.MovieService
import com.djumabaevs.movieswatch.model.Movie

class MovieRepository(private val movieService: MovieService) {

    private val movieLiveData = MutableLiveData<List<Movie>>()
    private val errorLiveData = MutableLiveData<String>()

    private val apiKey = "89691d3075f96e7c7e086344e47cfb18"
    fun fetchMovies() = movieService.getPopularMovies(apiKey)
}