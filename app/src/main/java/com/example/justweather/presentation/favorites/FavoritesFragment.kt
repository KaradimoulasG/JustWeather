package com.example.justweather.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.justweather.common.extensions.whenNotNullNorEmpty
import com.example.justweather.common.extensions.withNullOrEmpty
import com.example.justweather.databinding.FragmentFavoritesBinding
import com.example.justweather.domain.model.CityInfo
import com.example.justweather.presentation.BindingFragment
import com.example.justweather.presentation.WeatherViewModel
import com.example.justweather.presentation.WeatherViewModelEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoritesBinding::inflate

    private val viewModel: WeatherViewModel by sharedViewModel()
    private val favoritesAdapter = FavoritesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        setUpViewModel()
    }

    private fun setUpUi() {
        viewModel.getSavedCities()

        binding.favoritesRv.apply {
            layoutManager = LinearLayoutManager(
                activity?.applicationContext,
                LinearLayoutManager.VERTICAL,
                false,
            )
            adapter = favoritesAdapter
        }
    }

    private fun setUpViewModel() {
        lifecycleScope.launch {
            viewModel.state.onEach {
                when (it.eventName) {
                    WeatherViewModelEvent.None -> {}
                    WeatherViewModelEvent.SavedCities -> populateViews(viewModel.state.value.savedCitiesList)
                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun populateViews(savedCitiesList: List<CityInfo>) {
        savedCitiesList.withNullOrEmpty {
            // add empty view here
        }

        savedCitiesList.whenNotNullNorEmpty { favoritesAdapter.addAll(savedCitiesList) }
    }
}
