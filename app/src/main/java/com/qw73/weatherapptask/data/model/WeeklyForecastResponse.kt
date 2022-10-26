package com.qw73.weatherapptask.data.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeeklyForecastResponse(
    val city: City,
    val cod: String,
    val message: Double,
    val cnt: Int,
    val list: List<WeatherList>,
) {
    @JsonClass(generateAdapter = true)
    data class City(
        val id: Int,
        val name: String,
        val coord: Coord,
        val country: String,
        val population: Int,
        val timezone: Int,
    )
}

@JsonClass(generateAdapter = true)
data class ApiError(val cod: Int = 200, val message: String? = "")
