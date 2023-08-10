package com.example.justweather.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.justweather.R
import com.example.justweather.common.extensions.hide
import com.example.justweather.databinding.FragmentSearchBinding
import com.example.justweather.presentation.BindingFragment
import com.example.justweather.presentation.WeatherViewModel
import com.example.justweather.presentation.WeatherViewModelEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    private val viewModel: WeatherViewModel by sharedViewModel()
    private val searchAdapter = SearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        setUpViewModel()
    }

    private fun setUpUi() {
        binding.apply {
            resultsRv.apply {
                layoutManager = LinearLayoutManager(
                    activity?.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false,
                )
                adapter = searchAdapter

                searchComponent.apply {
                    cleanSearch { resultsTv.hide() }
                    eventKeyListener { viewModel.setCityToSearch(getSearchText()) }
                    search { viewModel.setCityToSearch(getSearchText()) }
                    textChangesListener()
                }
            }
        }
    }

    private fun setUpViewModel() {
        lifecycleScope.launch {
            viewModel.state.onEach {
                when (it.eventName) {
                    WeatherViewModelEvent.Loading -> timber.log.Timber.i("PAOK loading")
                    WeatherViewModelEvent.Fail -> timber.log.Timber.i("PAOK error")
                    WeatherViewModelEvent.GotSearchedCity -> findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
                    else -> {}
                }
            }.launchIn(this)
        }
    }
}
