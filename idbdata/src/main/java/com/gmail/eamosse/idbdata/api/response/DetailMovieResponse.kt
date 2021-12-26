package com.gmail.eamosse.idbdata.api.response

import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.DetailMovie
import com.google.gson.annotations.SerializedName

internal data class DetailMovieResponse(

    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("release_date")
    val date: String,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("backdrop_path")
    val backdrop_path: String
)

internal fun DetailMovieResponse.toDetailMovie() = DetailMovie(
    id = id,
    title = title,
    overview = overview,
    video = video,
    date = date,
    poster_path = poster_path,
    backdrop_path = backdrop_path
)
