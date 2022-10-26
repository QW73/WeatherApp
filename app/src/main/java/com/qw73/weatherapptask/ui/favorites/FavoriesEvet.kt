package com.qw73.weatherapptask.ui.favorites

import com.qw73.weatherapptask.ui.base.Event

sealed class FavoritesEvent : Event(){

    object NoFavoritesFoundEvent : FavoritesEvent()
}