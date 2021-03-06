package com.gmail.eamosse.idbdata.repository

import com.gmail.eamosse.idbdata.api.response.*
import com.gmail.eamosse.idbdata.api.response.toCategory
import com.gmail.eamosse.idbdata.api.response.toEntity
import com.gmail.eamosse.idbdata.api.response.toMovie
import com.gmail.eamosse.idbdata.api.response.toToken
import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.DetailMovie
import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Token
import com.gmail.eamosse.idbdata.datasources.LocalDataSource
import com.gmail.eamosse.idbdata.datasources.OnlineDataSource
import com.gmail.eamosse.idbdata.local.entities.FavoriteEntity
import com.gmail.eamosse.idbdata.utils.Result
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * La classe permettant de gérer les données de l'application
 */
class MovieRepository : KoinComponent {
    // Gestion des sources de données locales
    private val local: LocalDataSource by inject()

    // Gestion des sources de données en lignes
    private val online: OnlineDataSource by inject()

    /**
     * Récupérer le token permettant de consommer les ressources sur le serveur
     * Le résultat du datasource est converti en instance d'objets publiques
     */
    suspend fun getToken(): Result<Token> {
        return when (val result = online.getToken()) {
            is Result.Succes -> {
                // save the response in the local database
                local.saveToken(result.data.toEntity())
                // return the response
                Result.Succes(result.data.toToken())
            }
            is Result.Error -> result
        }
    }

    suspend fun getCategories(): Result<List<Category>> {
        return when (val result = online.getCategories()) {
            is Result.Succes -> {
                // On utilise la fonction map pour convertir les catégories de la réponse serveur
                // en liste de categories d'objets de l'application
                val categories = result.data.map {
                    it.toCategory()
                }
                Result.Succes(categories)
            }
            is Result.Error -> result
        }
    }

    suspend fun getMoviebyCategories(Id: String): Result<List<Movie>> {
        return when (val result = online.getMoviebyCategories(Id)) {
            is Result.Succes -> {
                // On utilise la fonction map pour convertir les catégories de la réponse serveur
                // en liste de categories d'objets de l'application
                val movie = result.data.map {
                    it.toMovie()
                }
                Result.Succes(movie)
            }
            is Result.Error -> result
        }
    }

    suspend fun getDetailMovie(Id: Int): Result<DetailMovie> {
        return when (val result = online.getDetailMovie(Id)) {
            is Result.Succes -> {
                // On utilise la fonction map pour convertir les catégories de la réponse serveur
                // en liste de categories d'objets de l'application
                val categories = result.data.toDetailMovie()
                Result.Succes(categories)
            }
            is Result.Error -> result
        }
    }
    suspend fun getFavoritesMovies(): List<FavoriteEntity> {
        return local.getFavoritesMovies()
    }
    suspend fun addToFavorite(favoriteMovie: FavoriteEntity) =
        local.addToFavorite(favoriteMovie)
    suspend fun checkMovie(id: String) = local.checkMovie(id)
    suspend fun removeFromFavorite(id: String) {
        local.removeFromFavorite(id)
    }
}
