package com.qw73.weatherapptask.data.api

import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.data.model.WeeklyForecastResponse
import com.qw73.weatherapptask.data.network.ApiService
import com.qw73.weatherapptask.util.API_KEY
import com.qw73.weatherapptask.util.IMAGE_BASE_URL
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override fun getIconUrl(iconId: String): String = "${IMAGE_BASE_URL}img/wn/$iconId@4x.png"

    override suspend fun getWeeklyForecast(
        longitude: Double,
        latitude: Double,
        unit: String,
    ): Response<WeeklyForecastResponse> = apiService.getWeeklyForecast(
        mapOf(
            QUERY_LONGITUDE to longitude.toString(),
            QUERY_LATITUDE to latitude.toString(),
            QUERY_UNITS to unit,
            QUERY_APP_ID to API_KEY
        )
    )

    override suspend fun searchCity(city: String): Response<List<CityResponse>> =
        apiService.searchCity(
            mapOf(
                QUERY_CITY to city,
                QUERY_LIMIT to "7",
                QUERY_APP_ID to API_KEY
            )
        )

}

private const val QUERY_APP_ID = "appid"
private const val QUERY_CITY = "q"
private const val QUERY_LONGITUDE = "lon"
private const val QUERY_LATITUDE = "lat"
private const val QUERY_UNITS = "units"
private const val QUERY_LIMIT = "limit"