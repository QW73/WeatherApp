package com.qw73.weatherapptask.data.api

import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.data.model.WeeklyForecastResponse
import retrofit2.Response

interface ApiHelper {

   fun getIconUrl(iconId: String): String

    suspend fun getWeeklyForecast(
        longitude: Double,
        latitude: Double,
        unit: String
    ): Response<WeeklyForecastResponse>

    suspend fun searchCity(city: String): Response<List<CityResponse>>

}
