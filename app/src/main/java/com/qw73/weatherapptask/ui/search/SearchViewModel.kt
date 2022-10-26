package com.qw73.weatherapptask.ui.search

import com.qw73.weatherapptask.data.Repo.MainRepo
import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.ui.base.BaseViewModel
import com.qw73.weatherapptask.util.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MainRepo) :
    BaseViewModel<SearchEvent>() {

    private val _cities: MutableStateFlow<List<CityResponse>> = MutableStateFlow(emptyList())
    val cities: StateFlow<List<CityResponse>> = _cities

    fun searchForCity(city: String) {
        startLoading()
        launchCoroutine {
            val result = repository.searchCity(city)
            when (result) {
                is DataResult.Failure -> result.cause.localizedMessage?.let {
                    sendEvent(SearchEvent.ErrorGettingResult(it))
                }
                is DataResult.Success -> with(result.value) {
                    _cities.value = this
                }
            }.also {
                endLoading()
            }
        }
    }

    fun onCityClicked(cityResponse: CityResponse) {
        TODO("Not yet implemented")
    }

}
