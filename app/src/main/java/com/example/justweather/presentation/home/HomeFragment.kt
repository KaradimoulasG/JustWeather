package com.example.justweather.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.justweather.R
import com.example.justweather.common.extensions.showWeatherIcon
import com.example.justweather.common.extensions.toDate
import com.example.justweather.databinding.FragmentHomeBinding
import com.example.justweather.domain.model.CityInfo
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
    private val forecastAdapter = ForecastAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpUi()
    }

    private fun setUpViewModel() {
        viewModel.getCityInfo(city = "Thessaloniki")

        lifecycleScope.launch {
            viewModel.state.onEach {
                when (it.eventName) {
                    WeatherViewModelEvent.Loading -> Timber.i("we wait")
                    WeatherViewModelEvent.GotCity -> populateMainViews(viewModel.state.value.apiResponse)
                    WeatherViewModelEvent.GotForecast -> populateForecastView(viewModel.state.value)
                    WeatherViewModelEvent.GotPollution -> {
                        binding.weatherRefresher.isRefreshing = false
                        populateAirPollutionView(viewModel.state.value)
                    }

                    WeatherViewModelEvent.CachedCity -> populateMainViews(viewModel.state.value.cachedCity)
                    WeatherViewModelEvent.EmptyFavouriteCity -> Timber.i("did not work")
                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun setUpUi() {
        binding.apply {
            forecastRv.apply {
                layoutManager = LinearLayoutManager(
                    activity?.applicationContext,
                    LinearLayoutManager.HORIZONTAL,
                    false,
                )
                adapter = forecastAdapter
            }

            weatherRefresher.apply {
                isRefreshing = false
                setOnRefreshListener { viewModel.getCityInfo("Thessaloniki") }
            }
        }
    }

    private fun populateMainViews(cityInfo: CityInfo?) {
        var date = ""
        val iconToShow = cityInfo?.weatherDetails?.get(0)?.icon?.showWeatherIcon()

        if (cityInfo == viewModel.state.value.apiResponse) {
            Timber.i("PAOK internet with ${viewModel.state.value.dateTime}")
            date = viewModel.state.value.dateTime!!.toDate()
            binding.threePieceComponent.setUpComponent(
                cityInfo?.windDetails?.speed!!.roundToInt(),
                cityInfo?.mainDetails?.humidity!!,
                cityInfo?.mainDetails?.pressure!!,
            )
        } else {
            Timber.i("PAOK no internet")
            date = cityInfo?.timestamp!!.toDate()
        }

        binding.apply {
            cityNameTv.text = getString(R.string.city_name_title, cityInfo?.cityName)
            dateTimeTv.text = date
            tempTv.text =
                getString(R.string.local_temp, cityInfo?.mainDetails?.temp?.roundToInt())
            realFeelTv.text = getString(R.string.real_feel, cityInfo?.mainDetails?.feels_like)
            Glide.with(this@HomeFragment).load(iconToShow).into(weatherIv)
        }
    }

    private fun populateForecastView(value: WeatherState) {
        forecastAdapter.addAll(value.forecastList)
    }

    private fun populateAirPollutionView(value: WeatherState) {
        binding.apply {
            airPollutionSlider.value = value.airPollutionDetails[0].main.aqi.toFloat()
        }
    }
}
