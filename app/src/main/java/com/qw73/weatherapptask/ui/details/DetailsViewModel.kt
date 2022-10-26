package com.qw73.weatherapptask.ui.details


import com.qw73.weatherapptask.data.Repo.MainRepo
import com.qw73.weatherapptask.data.model.TodayForecast
import com.qw73.weatherapptask.data.model.toCityResponse
import com.qw73.weatherapptask.ui.base.BaseViewModel
import com.qw73.weatherapptask.util.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: MainRepo) :
    BaseViewModel<DetailsEvent>() {

    private val _todayWeather: MutableStateFlow<TodayForecast?> = MutableStateFlow(null)
    val todayWeather: StateFlow<TodayForecast?> = _todayWeather

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun getDayWeatherDetails(latitude: Double, longitude: Double) {
        startLoading()
        launchCoroutine {
            handleResult(
                repository.getTodayForecast(latitude, longitude)
            )
        }
    }

    private fun handleResult(result: DataResult<TodayForecast>) {
        when (result) {
            is DataResult.Failure -> result.cause.localizedMessage?.let {
                sendEvent(DetailsEvent.ErrorGettingDetailsEvent(it))
            }
            is DataResult.Success -> with(result.value) {
                _todayWeather.value = this
                checkIsFavorite(this.cityId)
            }
        }.also {
            endLoading()
        }
    }

    private fun checkIsFavorite(cityId: Int) {
        launchCoroutine {
            repository.isCityFavorite(cityId).collect {
                _isFavorite.value = it
            }
        }
    }

    fun updateFavoriteState() {
        launchCoroutine {
            _todayWeather.value?.let {
                if (_isFavorite.value)
                    repository.removeCityFromFavorites(it.toCityResponse())
                else
                    repository.addCityToFavorites(it.toCityResponse())
            }
        }
    }

}
