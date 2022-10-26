package com.qw73.weatherapptask.data.network

import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.data.model.WeeklyForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    // http://api.openweathermap.org/geo/1.0/direct?q=Mo&limit=5&appid=d875a99a411c3c52d45d763ae33e6bd9

    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @QueryMap queryMap: Map<String, String>,
    ): Response<List<CityResponse>>

    // https://api.openweathermap.org/data/2.5/forecast/daily?lat=44.34&units=metric&lon=10.99&appid=886705b4c1182eb1c69f28eb8c520e20

    @GET("data/2.5/forecast/daily")
    suspend fun getWeeklyForecast(
        @QueryMap queryMap: Map<String, String>,
    ): Response<WeeklyForecastResponse>
}

/*
 @Query("q") q: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") appid: String,

 @Query("exclude") exclude: String = "minutely,alerts",
@Query("lon") lon: String,
@Query("lat") lat: String,
@Query("lang") lang: String = "ru",
@Query("units") units: String = "metric",
@Query("APPID") app_id: String = API_KEY,
*/
