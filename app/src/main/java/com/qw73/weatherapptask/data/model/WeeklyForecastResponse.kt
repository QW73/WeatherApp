package com.qw73.weatherapptask.data.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeeklyForecastResponse(
    val city: City = City(),
    val list: List<WeatherList> = listOf()
) {
    @JsonClass(generateAdapter = true)
    data class City(
        val coord: Coord = Coord(),
        val country: String = "",
        val id: Int = 0,
        val name: String = "",
        val timezone: Int = 0,
    )
}

@JsonClass(generateAdapter = true)
data class WeatherList(
    val deg: Int = 0,
    val dt: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val speed: Double = 0.0,
    val humidity: Int = 0,
    val temp: Temperature = Temperature(),
    val weather: List<Weather> = listOf()
) {
    @JsonClass(generateAdapter = true)
    data class Weather(
        val description: String = "",
        val icon: String = "",
        val id: Int = 0,
        val main: String = "",
    )

    @JsonClass(generateAdapter = true)
    data class Temperature(
        val day: Double = 0.0
    )
}

@JsonClass(generateAdapter = true)
data class Coord(
    val lat: Double = 0.0,
    val lon: Double = 0.0
)

@JsonClass(generateAdapter = true)
data class ApiError(val cod: Int = 200, val message: String? = "")
