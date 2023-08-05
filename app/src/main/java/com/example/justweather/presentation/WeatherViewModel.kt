package com.example.justweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justweather.common.extensions.onError
import com.example.justweather.common.extensions.onException
import com.example.justweather.common.extensions.onSuccess
import com.example.justweather.data.dto.airPollution.AirPollutionDetailsDto
import com.example.justweather.data.repositories.ICityRepo
import com.example.justweather.domain.model.ForecastInfo
import com.example.justweather.domain.model.toAirPollution
import com.example.justweather.domain.model.toCityInfo
import com.example.justweather.domain.model.toForecast
import com.example.justweather.domain.model.toForecastInfo
import com.example.justweather.domain.useCases.GetForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val cityRepo: ICityRepo,
    private val useCase: GetForecastUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    init {
        _state.value = WeatherState()
    }

    fun getCityInfo(city: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(eventName = WeatherViewModelEvent.Loading)
            }

            val response = cityRepo.getCityInfo(city)

            response.onSuccess { apiResponse ->
                val model = apiResponse.toCityInfo()
                timber.log.Timber.i("Favourite City here with ${cityRepo.getFavouriteCity()}")
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.GotCity,
                        cityName = city,
                        dateTime = model.timestamp,
                        currentTemp = model.mainDetails.temp,
                        realFeel = model.mainDetails.feels_like,
                        weatherIcon = model.weatherDetails[0].icon,
                        windSpeed = model.windDetails.speed,
                        pressure = model.mainDetails.pressure,
                        humidity = model.mainDetails.humidity,
                    )
                }
                getForecast(model.coordination.lat, model.coordination.lon)
            }.onException {
                _state.update {
                    it.copy(
                        eventName = WeatherViewModelEvent.Fail,
                    )
                }
            }.onError { _, _ ->
                _state.update {
                    it.copy(
                        eventName = WeatherViewModelEvent.Loading,
                    )
                }
            }
        }
    }

    private fun getForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            val response = useCase(lat, lon)

            response.onSuccess {
                val result = it.toForecast()
                val forecastList = result.list.toForecastInfo()
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.GotForecast,
                        forecastList = forecastList,
                    )
                }
                getPollution(lat, lon)
            }.onException {
                _state.update {
                    it.copy(
                        eventName = WeatherViewModelEvent.Fail,
                    )
                }
            }.onError { _, _ ->
                _state.update {
                    it.copy(
                        eventName = WeatherViewModelEvent.Loading,
                    )
                }
            }
        }
    }

    private fun getPollution(lat: Double, lon: Double) {
        viewModelScope.launch {
            val response = cityRepo.getAirPollution(lat, lon)

            response.onSuccess {
                val result = it.toAirPollution()
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.GotPollution,
                        airPollutionDetails = result.detailsList,
                    )
                }
            }.onException {
                _state.update {
                    it.copy(
                        eventName = WeatherViewModelEvent.Fail,
                    )
                }
            }.onError { _, _ ->
                _state.update {
                    it.copy(
                        eventName = WeatherViewModelEvent.Loading,
                    )
                }
            }
        }
    }
}

data class WeatherState(
    val eventName: WeatherViewModelEvent = WeatherViewModelEvent.None,
    val cityName: String = "",
    val dateTime: Int? = null,
    val currentTemp: Double = 0.0,
    val realFeel: Double = 0.0,
    val weatherIcon: String = "",
    val windSpeed: Double? = 0.0,
    val humidity: Int? = 0,
    val pressure: Int? = 0,
    val forecastList: List<ForecastInfo> = listOf(),
    val airPollutionDetails: List<AirPollutionDetailsDto> = listOf(),
    val message: String = "",
)

sealed class WeatherViewModelEvent() {
    object None : WeatherViewModelEvent()
    object Loading : WeatherViewModelEvent()
    object Success : WeatherViewModelEvent()
    object Fail : WeatherViewModelEvent()
    object Exception : WeatherViewModelEvent()
    object EmptyFavouriteCity : WeatherViewModelEvent()
    object SavedFavouriteCity : WeatherViewModelEvent()
    object GotCity : WeatherViewModelEvent()
    object GotForecast : WeatherViewModelEvent()
    object GotPollution : WeatherViewModelEvent()
}
