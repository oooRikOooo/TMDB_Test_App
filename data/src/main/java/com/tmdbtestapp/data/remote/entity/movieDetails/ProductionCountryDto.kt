package com.tmdbtestapp.data.remote.entity.movieDetails

import com.google.gson.annotations.SerializedName

data class ProductionCountryDto(
    @SerializedName("iso_3166_1")
    val iso_3166_1: String? = null,

    @SerializedName("name")
    val name: String? = null
)