package com.djumabaevs.movieswatch

import com.djumabaevs.movieswatch.api.MovieService

class MovieRepository(private val movieService: MovieService) {

    private val apiKey = "89691d3075f96e7c7e086344e47cfb18"
    fun fetchMovies() = movieService.getPopularMovies(apiKey)
}