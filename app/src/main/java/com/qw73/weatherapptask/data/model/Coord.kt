package com.qw73.weatherapptask.data.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    val lon: Double,
    val lat: Double,
)


