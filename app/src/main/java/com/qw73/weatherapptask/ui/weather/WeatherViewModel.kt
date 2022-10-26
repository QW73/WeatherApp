package com.qw73.weatherapptask.ui.weather

import android.location.Location
import androidx.annotation.VisibleForTesting
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.tasks.OnSuccessListener
import com.qw73.weatherapptask.data.Repo.MainRepo
import com.qw73.weatherapptask.data.model.Coord
import com.qw73.weatherapptask.data.model.WeekForecast
import com.qw73.weatherapptask.data.model.toCityResponse
import com.qw73.weatherapptask.ui.base.BaseViewModel
import com.qw73.weatherapptask.util.DataResult
import com.qw73.weatherapptask.util.extensions.toCoord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


private const val DEFAULT_HOUR = 5
private const val DEFAULT_MINUTE = 0

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: MainRepo
) :
    BaseViewModel<WeatherEvent>(), OnSuccessListener<Location?>,
    SwipeRefreshLayout.OnRefreshListener {

    private val _currentForecast: MutableStateFlow<WeekForecast?> = MutableStateFlow(null)
    val currentForecast: StateFlow<WeekForecast?> = _currentForecast

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite


    init {
        getLastKnownLocation()
        checkCurrentLocation()
    }

    private fun getLastKnownLocation() {
        launchCoroutine {
            repository.getLastKnownLocationForecast().collect {
                _currentForecast.emit(it)
            }
        }
    }

    @VisibleForTesting
    internal fun getData(coord: Coord) {
        startLoading()
        launchCoroutine {
            handleResult(
                repository.getWeekForecast(
                    latitude = coord.lat,
                    longitude = coord.lon
                )
            )
        }
    }

    @VisibleForTesting
    internal fun handleResult(result: DataResult<WeekForecast>) {
        when (result) {
            is DataResult.Failure -> result.cause.localizedMessage?.let {
                sendEvent(WeatherEvent.ErrorGettingForecastEvent(it))
            }
            is DataResult.Success -> with(result.value) {
                _currentForecast.value = this
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

    private fun checkCurrentLocation() = sendEvent(WeatherEvent.GetLocationEvent)

    override fun onSuccess(location: Location?) {
        if (location == null) {
            sendEvent(WeatherEvent.NoLocationDetectedEvent)
        } else {
            getData(location.toCoord())
        }
    }

    override fun onRefresh() {
        checkCurrentLocation()
    }

    fun updateFavoriteState() {
        _currentForecast.value?.let {
            launchCoroutine {
                if (_isFavorite.value)
                    repository.removeCityFromFavorites(it.toCityResponse())
                else
                    repository.addCityToFavorites(it.toCityResponse())
            }
        }
    }

}