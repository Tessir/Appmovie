package com.gmail.eamosse.idbdata.api.response

import com.gmail.eamosse.idbdata.data.Movie
import com.google.gson.annotations.SerializedName

data class MovieByCategorieResponse(
    @SerializedName("id")
    val idmovie: Int,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("genres")
    val genres: Genre
) {
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}

//internal fun MovieByCategorieResponse.toMovie() = Movie(
//    idmovie = idmovie,
//    original_title = original_title,
//    overview = overview,
//    genremovie = genres
//)
