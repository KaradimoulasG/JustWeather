package com.example.justweather.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.justweather.R
import com.example.justweather.common.extensions.hide
import com.example.justweather.common.extensions.show
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
        lifecycleScope.launch {
            viewModel.state.onEach {
                when (it.eventName) {
                    WeatherViewModelEvent.None -> viewModel.getCityInfo(city = "Athens")
                    WeatherViewModelEvent.Loading -> Timber.i("we wait")
                    WeatherViewModelEvent.GotCity,
                    WeatherViewModelEvent.GotSearchedCity,
                    -> {
                        populateMainViews(viewModel.state.value.apiResponse)
                        populateForecastView(viewModel.state.value)
                        binding.weatherRefresher.isRefreshing = false
                        populateAirPollutionView(viewModel.state.value)
                    }

                    WeatherViewModelEvent.CachedCity -> populateMainViews(viewModel.state.value.cachedCity, offlineMode = true)
//                    WeatherViewModelEvent.OfflineMode -> populateMainViews(viewModel.state.value.cachedCity, offlineMode = true)
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

    private fun populateMainViews(cityInfo: CityInfo?, offlineMode: Boolean = false) {
        val iconToShow = cityInfo?.weatherDetails?.get(0)?.icon?.showWeatherIcon()
        val date = when (cityInfo) {
            viewModel.state.value.apiResponse -> viewModel.state.value.dateTime!!.toDate()
            else -> cityInfo?.timestamp!!.toDate()
        }

        binding.apply {
            threePieceComponent.setUpComponent(
                cityInfo?.windDetails?.speed!!.roundToInt(),
                cityInfo?.mainDetails?.humidity!!,
                cityInfo?.mainDetails?.pressure!!,
            )

            cityNameTv.text = getString(R.string.city_name_title, cityInfo?.cityName)
            dateTimeTv.text = getString(R.string.date_time_title, date)
            tempTv.text =
                getString(R.string.local_temp, cityInfo?.mainDetails?.temp?.roundToInt())
            realFeelTv.text = getString(R.string.real_feel, cityInfo?.mainDetails?.feels_like)
            Glide.with(this@HomeFragment).load(iconToShow).into(weatherIv)

            when (offlineMode) {
                true -> {
                    fiveDayForecastNsv.hide()
                    airPollutionLayout.hide()
                    offlineModeTv.show()
                }
                else -> {
                    fiveDayForecastNsv.show()
                    airPollutionLayout.show()
                    offlineModeTv.hide()
                }
            }
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
