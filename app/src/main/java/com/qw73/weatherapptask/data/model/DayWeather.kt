package com.qw73.weatherapptask.data.model

import java.io.Serializable

data class DayWeather(
    val weekDay: String = "",
    val date: String = "",
    val temperature: String = "",
    val sunrise: String = "",
    val sunset: String = "",
    val description: String = "",
    val windSpeed: String = "",
    val humidity: String = "",
    val iconUrl: String = "",
    val unit: DegreeUnit = DegreeUnit.CELSIUS
) : Serializable

enum class DegreeUnit {
    CELSIUS, FAHRENHEIT
}

fun DegreeUnit.getDegreeFormat(): String = when (this) {
    DegreeUnit.CELSIUS -> "metric"
    DegreeUnit.FAHRENHEIT -> "imperial"
}