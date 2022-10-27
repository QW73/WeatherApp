package com.qw73.weatherapptask.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.qw73.weatherapptask.R
import com.qw73.weatherapptask.data.model.CityResponse
import com.qw73.weatherapptask.databinding.FragmentFavoritesBinding
import com.qw73.weatherapptask.ui.base.BaseFragment
import com.qw73.weatherapptask.ui.search.CityAdapter
import com.qw73.weatherapptask.util.extensions.showToast

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesEvent>() {

    override val viewModel by viewModels<FavoritesViewModel>()

    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CityAdapter {
            openWeatherDetails(it)
        }
    }

    private fun openWeatherDetails(cityResponse: CityResponse) =
        navigateTo(
            FavoritesFragmentDirections.actionFavoritesToDetails(
                cityResponse.lat.toFloat(),
                cityResponse.lon.toFloat()
            )
        )

    override fun observeData() {
        super.observeData()
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                favorites.flowWithLifecycle(lifecycle)
                    .collect {
                        adapter.submitList(it)
                    }
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentFavoritesBinding =
        FragmentFavoritesBinding.inflate(inflater, container, false)

    override fun handleEvent(event: FavoritesEvent) {
        when (event) {
            FavoritesEvent.NoFavoritesFoundEvent -> showToast(R.string.no_favorites_found)
        }
    }

    override fun setupViews() {
        binding.rvFavorites.adapter = adapter
    }

}