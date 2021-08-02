package com.djumabaevs.movieswatch.model

data class PopularMoviesResponse (
    val page: Int,
    val results: List<Movie>
)