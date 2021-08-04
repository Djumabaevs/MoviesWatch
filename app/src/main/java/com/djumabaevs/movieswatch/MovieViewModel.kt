package com.djumabaevs.movieswatch

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.djumabaevs.movieswatch.model.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieViewModel(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val popularMoviesLiveData = MutableLiveData<List<Movie>>()
    private val errorLiveData = MutableLiveData<String>()
    val popularMovies: LiveData<List<Movie>>
        get() = popularMoviesLiveData
    val error: LiveData<String>
        get() = errorLiveData
    private var disposable = CompositeDisposable()


    fun fetchPopularMovies() {
        disposable.add(movieRepository.fetchMovies()
            .subscribeOn(Schedulers.io())
//            .map { it.results }
            .flatMap { Observable.fromIterable(it.results) }
            .filter {
                val cal = Calendar.getInstance()
                cal.add(Calendar.MONTH, -1)
                it.release_date.startsWith(
                    "${cal.get(Calendar.YEAR)} - ${cal.get(Calendar.MONTH) + 1}"
                )
            }
            .
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                popularMoviesLiveData.postValue(it)
            }, { error ->
                errorLiveData.postValue("An error occurred:${error.message}")
            })
        )
    }
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


    }