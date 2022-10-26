package com.qw73.weatherapptask.ui.search

import com.qw73.weatherapptask.ui.base.Event

sealed class SearchEvent : Event() {

    data class ErrorGettingResult(val errorMessage: String) : SearchEvent()

}