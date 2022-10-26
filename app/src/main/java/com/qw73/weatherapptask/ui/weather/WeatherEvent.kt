package com.qw73.weatherapptask.ui.weather

import com.qw73.weatherapptask.ui.base.Event

sealed class WeatherEvent : Event() {

    object NoLocationDetectedEvent : WeatherEvent()
    object GetLocationEvent : WeatherEvent()
    data class ErrorGettingForecastEvent(val errorMessage: String) : WeatherEvent()
}