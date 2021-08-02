package com.djumabaevs.movieswatch.api

import com.djumabaevs.movieswatch.model.PopularMoviesResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String):
            Observable<PopularMoviesResponse>
}