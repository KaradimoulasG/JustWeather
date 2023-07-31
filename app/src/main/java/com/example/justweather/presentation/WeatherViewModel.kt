package com.example.justweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justweather.common.extensions.onError
import com.example.justweather.common.extensions.onException
import com.example.justweather.common.extensions.onSuccess
import com.example.justweather.data.repositories.ICityRepo
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
            val response = cityRepo.getCityInfo()

            response.onSuccess { response ->
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.Success,
                    )
                }
                getForecast(response.coord.lat, response.coord.lon)
                getPollution(response.coord.lat, response.coord.lon)
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
                        eventName = WeatherViewModelEvent.Success,
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
                        eventName = WeatherViewModelEvent.Success,
                    )
                }
            }
        }
    }
}

data class WeatherState(
    val eventName: WeatherViewModelEvent = WeatherViewModelEvent.None,
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
}
