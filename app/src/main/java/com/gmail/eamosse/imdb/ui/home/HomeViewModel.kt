package com.gmail.eamosse.imdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.DetailMovie
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.local.entities.FavoriteEntity
import com.gmail.eamosse.idbdata.repository.MovieRepository
import com.gmail.eamosse.idbdata.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * VM permettant de gérer les données de la vue
 */
class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _token: MutableLiveData<Token> = MutableLiveData()
    val token: LiveData<Token>
        get() = _token

    private val _moviedetail: MutableLiveData<DetailMovie> = MutableLiveData()
    val movieDetail: LiveData<DetailMovie>
        get() = _moviedetail

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _moviesbycategory: MutableLiveData<List<Movie>> = MutableLiveData()
    val moviesbycategory: LiveData<List<Movie>>
        get() = _moviesbycategory

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    /**
     * Block d'initialisation du viewmodel
     * On en profite (pour l'instant) pour récupérer le token utilisateur
     */
    init {

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getToken()) {
                is Result.Succes -> {
                    _token.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getCategories()) {
                is Result.Succes -> {
                    _categories.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getMoviebyCategories(Id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getMoviebyCategories(Id)) {
                is Result.Succes -> {
                    _moviesbycategory.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getDetailMovie(Id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getDetailMovie(Id)) {
                is Result.Succes -> {
                    _moviedetail.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun addToFavorite(movie: DetailMovie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteEntity(
                    movie.id.toString(),
                    movie.backdrop_path,
                    movie.title,
                    movie.overview,
                    movie.poster_path,
                    movie.video,
                    movie.date
                )
            )
        }
    }

    suspend fun checkMovie(id: String) = repository.checkMovie(id)

    fun removeFromFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }
}
