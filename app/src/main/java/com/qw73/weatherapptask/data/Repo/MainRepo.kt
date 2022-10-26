package com.qw73.weatherapptask.data.Repo

import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.data.model.DegreeUnit
import com.qw73.weatherapptask.data.model.TodayForecast
import com.qw73.weatherapptask.data.model.WeekForecast
import com.qw73.weatherapptask.util.DataResult
import kotlinx.coroutines.flow.Flow

interface MainRepo {

    suspend fun searchCity(query: String): DataResult<List<CityResponse>>
    suspend fun getWeekForecast(latitude: Double, longitude: Double): DataResult<WeekForecast>
    suspend fun getTodayForecast(latitude: Double, longitude: Double): DataResult<TodayForecast>
    suspend fun updateCurrentLocationForecast(weekForecast: WeekForecast)
    fun getLastKnownLocationForecast(): Flow<WeekForecast?>

    suspend fun getDegreeUnit(): DegreeUnit
    suspend fun setDegreeUnit(degreeUnit: DegreeUnit)

    fun getFavorites(): Flow<List<CityResponse>>
    fun isCityFavorite(cityId: Int): Flow<Boolean>
    suspend fun addCityToFavorites(cityResponse: CityResponse)
    suspend fun removeCityFromFavorites(cityResponse: CityResponse)
}