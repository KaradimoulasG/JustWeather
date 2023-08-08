package com.example.justweather.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.justweather.common.extensions.hide
import com.example.justweather.databinding.FragmentSearchBinding
import com.example.justweather.presentation.BindingFragment
import com.example.justweather.presentation.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    private val viewModel: WeatherViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        binding.apply {
            searchComponent.apply {
                cleanSearch { resultsTv.hide() }
                eventKeyListener { viewModel.getCityInfo(getSearchText()) }
                search { viewModel.getCityInfo(getSearchText()) }
            }
        }
    }
}
