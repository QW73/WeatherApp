package com.qw73.weatherapptask.ui.favorites

import com.qw73.weatherapptask.data.Repo.MainRepo
import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: MainRepo) :
    BaseViewModel<FavoritesEvent>() {

    private val _favorites: MutableStateFlow<List<CityResponse>> = MutableStateFlow(emptyList())
    val favorites: StateFlow<List<CityResponse>> = _favorites

    init {
        getFavoriteCities()
    }

    private fun getFavoriteCities() = launchCoroutine {
        repository.getFavorites().collect {
            if (it.isEmpty())
                sendEvent(FavoritesEvent.NoFavoritesFoundEvent)
            _favorites.emit(it)
        }
    }
}
