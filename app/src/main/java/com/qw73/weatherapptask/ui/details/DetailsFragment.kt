package com.qw73.weatherapptask.ui.details

import android.os.Bundle
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
import com.qw73.weatherapptask.ui.weather.DayAdapter
import com.qw73.weatherapptask.util.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsEvent>() {

    override val viewModel by viewModels<DetailsViewModel>()
    private val navigationArgs: DetailsFragmentArgs by navArgs()
    private lateinit var adapter: DayAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = DayAdapter()
    }

    override fun handleEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.ErrorGettingDetailsEvent -> showToast(event.errorMessage)
        }
    }

    override fun observeData() {
        super.observeData()
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                isLoading.flowWithLifecycle(lifecycle)
                    .collect {
                        binding.progressBar.visibility = when (it) {
                            true -> View.VISIBLE
                            false -> View.GONE
                        }
                    }

            }

            viewLifecycleOwner.lifecycleScope.launch {
                currentForecast.flowWithLifecycle(lifecycle)
                    .collect {
                        it?.let {
                            renderDays(it.weatherDays)
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

    private fun renderDays(dayWeathers: List<DayWeather>) {
        adapter.submitList(dayWeathers)
        if (dayWeathers.isNotEmpty())
            bindFirstDay(dayWeathers.first())
    }

    private fun bindFirstDay(first: DayWeather) {
        val degreeUnitResId = when (first.unit) {
            DegreeUnit.CELSIUS -> R.string.temperature_c
            DegreeUnit.FAHRENHEIT -> R.string.temperature_f
        }

        with(binding) {
            header.visibility = View.VISIBLE
            tDay.text = first.weekDay
            tDate.text = first.date
            tDegree.text = getString(degreeUnitResId, first.temperature)
            Glide.with(requireContext())
                .load(first.iconUrl)
                .into(ivWeather)
        }
    }

    override fun setupViews() {
        val latitude = navigationArgs.latitude.toDouble()
        val longitude = navigationArgs.longitude.toDouble()

        viewModel.getDetailsData(latitude, longitude)
        with(binding) {
            recyclerViewWords.adapter = adapter
            ivFavorites.setOnClickListener {
                viewModel.updateFavoriteState()
            }
        }
        binding.ivFavorites.setOnClickListener {
            viewModel.updateFavoriteState()
        }
    }
}