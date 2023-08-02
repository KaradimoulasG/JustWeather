package com.example.justweather.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.justweather.R
import com.example.justweather.common.extensions.showWeatherIcon
import com.example.justweather.common.extensions.transformToDate
import com.example.justweather.databinding.FragmentHomeBinding
import com.example.justweather.presentation.BindingFragment
import com.example.justweather.presentation.WeatherState
import com.example.justweather.presentation.WeatherViewModel
import com.example.justweather.presentation.WeatherViewModelEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import kotlin.math.roundToInt

class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: WeatherViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        populateMainViews(viewModel.state.value)
    }

    private fun setUpViewModel() {
//        viewModel.populateScreenWithFavouriteCity()
        viewModel.testingCase()

        lifecycleScope.launch {
            viewModel.state.onEach {
                when (it.eventName) {
                    WeatherViewModelEvent.Loading -> Timber.i("PAOK we wait")
                    WeatherViewModelEvent.GotCity -> populateMainViews(viewModel.state.value)
                    WeatherViewModelEvent.EmptyFavouriteCity -> Timber.i("did not work")
                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun populateMainViews(stateValue: WeatherState) {
        val date = stateValue.dateTime?.transformToDate()
        val iconToShow = stateValue.weatherIcon.showWeatherIcon()

        binding.apply {
            cityNameTv.text = getString(R.string.city_name_title, stateValue.cityName)
            dateTimeTv.text = getString(R.string.date_time_title, date)
            tempTv.text = getString(R.string.local_temp, stateValue.currentTemp.roundToInt())
            realFeelTv.text = getString(R.string.real_feel, stateValue.realFeel)
            Glide.with(this@HomeFragment).load(iconToShow).into(weatherIv)

            threePieceComponent.setUpComponent(
                stateValue.windSpeed!!.roundToInt(),
                stateValue.humidity!!,
                stateValue.pressure!!,
            )
        }
    }
}
