package com.qw73.weatherapptask.ui.settings

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.qw73.weatherapptask.R
import com.qw73.weatherapptask.data.model.DegreeUnit
import com.qw73.weatherapptask.databinding.FragmentSettingsBinding
import com.qw73.weatherapptask.ui.base.BaseFragment
import com.qw73.weatherapptask.ui.base.NoEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, NoEvent>() {

    override val viewModel by viewModels<SettingsViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun setupViews() {
        renderAppSettings(viewModel.degreeUnit)

        binding.temperatureRadioGroup.setOnCheckedChangeListener { _, idRes ->
            val newDegreeUnit = if (idRes == R.id.button_fahrenheit)
                DegreeUnit.FAHRENHEIT
            else
                DegreeUnit.CELSIUS

            viewModel.changeTemperatureUnit(newDegreeUnit)
        }
    }

    override fun observeData() {
        super.observeData()
        renderAppSettings(viewModel.degreeUnit)
    }

    override fun handleEvent(event: NoEvent) = Unit

    private fun renderAppSettings(degreeUnit: DegreeUnit) {
        val radioButton = when (degreeUnit) {
            DegreeUnit.CELSIUS -> R.id.button_celsius
            DegreeUnit.FAHRENHEIT -> R.id.button_fahrenheit
        }
        if (binding.temperatureRadioGroup.checkedRadioButtonId != radioButton)
            binding.temperatureRadioGroup.check(radioButton)
    }

}