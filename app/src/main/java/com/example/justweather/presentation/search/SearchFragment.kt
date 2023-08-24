package com.example.justweather.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.core_domain.databinding.FragmentSearchBinding
import com.example.core_domain.domain.common.extensions.hide
import com.example.justweather.presentation.BindingFragment
import com.example.justweather.presentation.WeatherViewModel
import com.example.justweather.presentation.WeatherViewModelEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.example.core_domain.R as R2

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    private val viewModel: WeatherViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        setUpViewModel()
    }

    private fun setUpUi() {
        binding.apply {
            searchComponent.apply {
                cleanSearch { resultsTv.hide() }
                eventKeyListener { viewModel.setCityToSearch(getSearchText()) }
                search { viewModel.setCityToSearch(getSearchText()) }
                textChangesListener()
            }
        }
    }

    private fun setUpViewModel() {
        lifecycleScope.launch {
            viewModel.state.onEach {
                when (it.eventName) {
                    WeatherViewModelEvent.Loading -> timber.log.Timber.i("Loading")
                    WeatherViewModelEvent.Fail -> timber.log.Timber.i("Error")
                    WeatherViewModelEvent.GotSearchedCity -> findNavController().navigate(R2.id.action_searchFragment_to_homeFragment)
                    else -> {}
                }
            }.launchIn(this)
        }
    }
}
