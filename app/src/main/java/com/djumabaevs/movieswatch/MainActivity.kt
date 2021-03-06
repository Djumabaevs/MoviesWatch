package com.djumabaevs.movieswatch

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.djumabaevs.movieswatch.model.Movie

class MainActivity : AppCompatActivity() {

    private val movieAdapter by lazy {
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                openMovieDetails(movie)
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.google.com")
        setContentView(webView)*/

        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

//        getMovies()

        val movieRepository = (application as MovieApplication).movieRepository
        val movieViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieViewModel(movieRepository) as T
            }
        }).get(MovieViewModel::class.java)

        movieViewModel.popularMovies.observe(this, {popularMovies ->
            movieAdapter.addMovies(popularMovies)
            /*    .filter {
                    it.release_date.startsWith(
                        java.util.Calendar.getInstance().get(Calendar.YEAR).toString()
                    )
                }
                .sortedBy {
                    it.title
                }*/
        })

        movieViewModel.getError().observe(this, {error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })

    }

/*    private fun getMovies() {
        val movieRepository = (application as
                MovieApplication).movieRepository
        val movieViewModel = ViewModelProvider(this, object :
            ViewModelProvider.Factory {
            override fun <T : ViewModel?>
                    create(modelClass: Class<T>): T {
                return MovieViewModel(movieRepository) as T
            }
        }).get(MovieViewModel::class.java)

        movieViewModel.fetchPopularMovies()
        movieViewModel.popularMovies
            .observe(this, { popularMovies ->
                movieAdapter.addMovies(popularMovies)
            })
        movieViewModel.error.observe(this, { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })
    }*/

    private fun openMovieDetails(movie: Movie) {
        val intent =
            Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_MOVIE, movie)
            }
        startActivity(intent)
    }
}