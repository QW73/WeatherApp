package com.qw73.weatherapptask.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherList(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>,
    val speed: Double,
    val deg: Int,
    val gust: Double,
    val clouds: Int,
    val pop: Double,
    val rain: Double,
) {
    @JsonClass(generateAdapter = true)
    data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String,
    )

    @JsonClass(generateAdapter = true)
    data class Temp(
        val day: Double,
        val min: Double,
        val max: Double,
        val night: Double,
        val eve: Double,
        val morn: Double,
    )
}
