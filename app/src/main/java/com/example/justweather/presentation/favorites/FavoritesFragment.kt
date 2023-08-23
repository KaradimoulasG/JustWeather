package com.example.justweather.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.justweather.R
import com.example.justweather.common.extensions.hide
import com.example.justweather.common.extensions.show
import com.example.justweather.common.extensions.whenNotNullNorEmpty
import com.example.justweather.common.extensions.withNullOrEmpty
import com.example.justweather.databinding.FragmentFavoritesBinding
import com.example.justweather.domain.model.CityInfo
import com.example.justweather.presentation.BindingFragment
import com.example.justweather.presentation.WeatherViewModel
import com.example.justweather.presentation.WeatherViewModelEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoritesBinding::inflate

    private val viewModel: WeatherViewModel by sharedViewModel()
    private val favoritesAdapter: FavoritesAdapter by lazy { FavoritesAdapter(layoutInflater = layoutInflater, ::onCityClick, ::onCityLongClick) }

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
                timber.log.Timber.i("PAOK the event is ${it.eventName}")
                when (it.eventName) {
                    WeatherViewModelEvent.None -> {}
                    WeatherViewModelEvent.SavedCities -> populateViews(viewModel.state.value.savedCitiesList)
                    WeatherViewModelEvent.GotCity -> findNavController().navigate(R.id.action_favoritesFragment_to_homeFragment)
                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun populateViews(savedCitiesList: List<CityInfo>) {
        savedCitiesList.withNullOrEmpty { binding.emptyScreenTv.show() }

        savedCitiesList.whenNotNullNorEmpty {
            binding.emptyScreenTv.hide()
            favoritesAdapter.addAll(savedCitiesList)
        }
    }

    private fun onCityClick(cityName: String) {
        viewModel.setCityToSearch(cityName, cameFromSavedCities = true)
    }

    private fun onCityLongClick(cityName: String) {
        // TODO add long click screen for city deletion
        MaterialAlertDialogBuilder(activity?.applicationContext!!)
            .setTitle("Do you want to remove this saved city")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
            }
    }
}
