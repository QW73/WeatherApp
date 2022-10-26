package com.qw73.weatherapptask.ui.details

import com.qw73.weatherapptask.ui.base.Event

sealed class DetailsEvent : Event(){

    data class ErrorGettingDetailsEvent(val errorMessage: String) : DetailsEvent()
}