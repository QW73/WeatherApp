package com.qw73.weatherapptask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.data.model.WeekForecast

@Database(entities = [CityResponse::class, WeekForecast::class], version = 1 )
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao

    companion object {
        const val DATABASE_NAME = "weather-db"
    }
}