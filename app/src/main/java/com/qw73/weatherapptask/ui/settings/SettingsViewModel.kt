package com.qw73.weatherapptask.ui.settings

import com.qw73.weatherapptask.data.Repo.MainRepo
import com.qw73.weatherapptask.data.model.DegreeUnit
import com.qw73.weatherapptask.ui.base.BaseViewModel
import com.qw73.weatherapptask.ui.base.NoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: MainRepo) :
    BaseViewModel<NoEvent>() {

    var degreeUnit = DegreeUnit.CELSIUS
        private set

    init {
        getCurrentSettings()
    }

    private fun getCurrentSettings() = launchCoroutine {
        degreeUnit = repository.getDegreeUnit()
    }

    fun changeTemperatureUnit(degreeUnit: DegreeUnit) = launchCoroutine {
        repository.setDegreeUnit(degreeUnit)
    }

}
