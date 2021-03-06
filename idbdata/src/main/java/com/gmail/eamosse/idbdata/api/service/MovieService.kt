package com.gmail.eamosse.idbdata.api.service

import com.gmail.eamosse.idbdata.api.response.CategoryResponse
import com.gmail.eamosse.idbdata.api.response.DetailMovieResponse
import com.gmail.eamosse.idbdata.api.response.MoviesResponse
import com.gmail.eamosse.idbdata.api.response.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieService {
    @GET("authentication/token/new")
    suspend fun getToken(): Response<TokenResponse>

    @GET("genre/movie/list")
    suspend fun getCategories(): Response<CategoryResponse>

    //    @GET("movie/{movie_id}")
    //    suspend fun getMoviebyCategories(): Response<MovieByCategorieResponse>
    @GET("discover/movie")
    suspend fun getMoviebyCategories(@Query("with_genres") Id: String): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(@Path("movie_id") Id: Int): Response<DetailMovieResponse>
}
