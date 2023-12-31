package com.example.justweather.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.core_domain.databinding.FragmentHomeBinding
import com.example.core_domain.domain.common.delegates.ITopToast
import com.example.core_domain.domain.common.delegates.TopToastDelegate
import com.example.core_domain.domain.common.extensions.hide
import com.example.core_domain.domain.common.extensions.show
import com.example.core_domain.domain.common.extensions.showWeatherIcon
import com.example.core_domain.domain.common.extensions.toDate
import com.example.justweather.R
import com.example.core_domain.R as R2
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

class HomeFragment :
    BindingFragment<FragmentHomeBinding>(),
    ITopToast by TopToastDelegate() {

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
                    WeatherViewModelEvent.None -> {}
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
                    WeatherViewModelEvent.EmptyFavouriteCity -> Timber.i("did not work")
                    WeatherViewModelEvent.NetworkException -> Timber.i("network exception here")
                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun setUpUi() {
        binding.apply {
            registerTopToastDelegate(requireActivity(), root)

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

    private fun populateMainViews(cityInfo: com.example.core_domain.domain.model.CityInfo?, offlineMode: Boolean = false) {
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

            cityNameTv.text = getString(R2.string.city_name_title, cityInfo?.cityName)
            dateTimeTv.text = getString(R2.string.date_time_title, date)
            tempTv.text =
                getString(R2.string.local_temp, cityInfo?.mainDetails?.temp?.roundToInt())
            realFeelTv.text = getString(R2.string.real_feel, cityInfo?.mainDetails?.feels_like)
            Glide.with(this@HomeFragment).load(iconToShow).into(weatherIv)

            when (offlineMode) {
                true -> {
                    Toast.makeText(requireActivity(), "something", Toast.LENGTH_SHORT).show()
                    showTopToast(isError = true, getString(R2.string.top_toast_internet_error))
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
