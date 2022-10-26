package com.qw73.weatherapptask.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.qw73.weatherapptask.R
import com.qw73.weatherapptask.data.model.DayWeather
import com.qw73.weatherapptask.data.model.DegreeUnit
import com.qw73.weatherapptask.data.model.toCityResponse
import com.qw73.weatherapptask.databinding.FragmentDetailsBinding
import com.qw73.weatherapptask.ui.base.BaseFragment
import com.qw73.weatherapptask.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsEvent>() {

    override val viewModel by viewModels<DetailsViewModel>()
    private val navigationArgs: DetailsFragmentArgs by navArgs()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

    override fun handleEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.ErrorGettingDetailsEvent -> showToast(event.errorMessage)
        }
    }

    override fun observeData() {
        super.observeData()
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                todayWeather.flowWithLifecycle(lifecycle)
                    .collect {
                        it?.let {
                            bindDay(it.weatherDay)
                            binding.tIty.text = it.toCityResponse().name
                        }
                    }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                isFavorite.flowWithLifecycle(lifecycle)
                    .collect {
                        val favoriteResId = if (it)
                            R.drawable.ic_nav_favorite_24dp
                        else
                            R.drawable.ic_heart_27dp
                        binding.ivFavorites.setImageResource(favoriteResId)
                    }
            }
        }
    }

    private fun bindDay(dayWeather: DayWeather) {
        with(binding) {
            val degreeUnitResId = when (dayWeather.unit) {
                DegreeUnit.CELSIUS -> R.string.temperature_c
                DegreeUnit.FAHRENHEIT -> R.string.temperature_f
            }
            header.visibility = View.VISIBLE
            tDay.text = dayWeather.weekDay
            tDate.text = dayWeather.date
            tDegree.text = getString(degreeUnitResId, dayWeather.temperature)
            Glide.with(requireContext())
                .load(dayWeather.iconUrl)
                .into(ivWeather)
        }
    }

    override fun setupViews() {
        val latitude = navigationArgs.latitude.toDouble()
        val longitude = navigationArgs.longitude.toDouble()

        viewModel.getDayWeatherDetails(latitude, longitude)
        binding.ivFavorites.setOnClickListener {
            viewModel.updateFavoriteState()
        }
    }
}