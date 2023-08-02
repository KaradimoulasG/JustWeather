package com.example.justweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justweather.common.extensions.onError
import com.example.justweather.common.extensions.onException
import com.example.justweather.common.extensions.onSuccess
import com.example.justweather.data.repositories.ICityRepo
import com.example.justweather.domain.model.toCityInfo
import com.example.justweather.domain.model.toForecast
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

//    fun populateScreenWithFavouriteCity() {
//        viewModelScope.launch {
//            val response = cityRepo.getFavouriteCity()
//
//            timber.log.Timber.i("the response is $response")
//
//            when (response?.cityName?.isEmpty()) {
//                true -> {
//                    _state.update { state ->
//                        state.copy(eventName = WeatherViewModelEvent.EmptyFavouriteCity)
//                    }
//                }
//                else -> {
//                    _state.update { state ->
//                        state.copy(eventName = WeatherViewModelEvent.SavedFavouriteCity)
//                    }
//                }
//            }
//        }
//    }

    fun testingCase() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(eventName = WeatherViewModelEvent.Loading)
            }

            val city = "Thessaloniki"
            val response = cityRepo.getCityInfo(city)

            response.onSuccess { apiResponse ->
                val model = apiResponse.toCityInfo()
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.GotCity,
                        cityName = city,
                        dateTime = model.timestamp,
                        currentTemp = model.main.temp,
                        realFeel = model.main.feels_like,
                        weatherIcon = model.weather[0].icon,
                        windSpeed = model.wind.speed,
                        pressure = model.main.pressure,
                        humidity = model.main.humidity,
                    )
                }
                getForecast(model.coordination.lat, model.coordination.lon)
                getPollution(model.coordination.lat, model.coordination.lon)
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
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.GotForecast,
                    )
                }
                it.toForecast()
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
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.GotPollution,
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
