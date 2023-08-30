package com.example.justweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.domain.common.extensions.onError
import com.example.core_domain.domain.common.extensions.onException
import com.example.core_domain.domain.common.extensions.onSuccess
import com.example.core_domain.domain.data.repositories.ICityRepo
import com.example.core_domain.domain.model.toAirPollution
import com.example.core_domain.domain.model.toCityInfo
import com.example.core_domain.domain.model.toForecast
import com.example.core_domain.domain.model.toForecastInfo
import com.example.core_domain.domain.useCases.GetForecastUseCase
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
            val cachedCity = cityRepo.getFavouriteCity()
            _state.update { state ->
                state.copy(
                    eventName = if (cachedCity == null) WeatherViewModelEvent.NoCachedCity else WeatherViewModelEvent.Loading,
                    cachedCityName = if (cachedCity == null) "" else cachedCity.cityName,
                )
            }
            val cityToSearch =
                _state.value.searchedCity.ifEmpty {
                    when (_state.value.cachedCityName) {
                        city -> _state.value.cachedCityName
                        else -> city
                    }
                }
            val response = cityRepo.getCityInfo(cityToSearch)

            response.onSuccess { apiResponse ->
                val model = apiResponse.toCityInfo()
                _state.update { state ->
                    state.copy(
                        eventName = if (cityRepo.checkIfCityIsSaved(model.cityName)) WeatherViewModelEvent.SavedFavouriteCity else WeatherViewModelEvent.NotSavedFavouriteCity,
                        apiResponse = model,
                        savedCity = cityRepo.checkIfCityIsSaved(model.cityName),
                        cachedCity = model,
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
            }.onException { handleOfflineMode() }
                .onError { _, _ -> handleOfflineMode() }
        }
    }

    private fun getForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            val response = useCase(lat, lon)

            response.onSuccess {
                val result = it.toForecast()
                val forecastList = result.list.toForecastInfo()
                _state.update { state ->
                    state.copy(forecastList = forecastList)
                }
                getPollution(lat, lon)
            }.onException {
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.NetworkException,
                        errorMessage = it.message,
                    )
                }
            }.onError { code, message ->
                _state.update {
                    it.copy(
                        eventName = WeatherViewModelEvent.ApiError,
                        errorMessage = message,
                        errorCode = code,
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
                        eventName = if (_state.value.searchedCity.isNullOrEmpty()) WeatherViewModelEvent.GotCity else WeatherViewModelEvent.GotSearchedCity,
                        airPollutionDetails = result.detailsList,
                    )
                }
            }.onException {
                _state.update { state ->
                    state.copy(
                        eventName = WeatherViewModelEvent.NetworkException,
                        errorMessage = it.message,
                    )
                }
            }.onError { code, message ->
                _state.update {
                    it.copy(
                        eventName = WeatherViewModelEvent.ApiError,
                        errorMessage = message,
                        errorCode = code,
                    )
                }
            }
        }
    }

    fun setCityToSearch(cityName: String, cameFromSavedCities: Boolean = false) {
        _state.update { it.copy(searchedCity = if (cameFromSavedCities) "" else cityName) }
        getCityInfo(cityName)
    }

    fun saveFavouriteCity() {
        viewModelScope.launch {
            _state.value.apiResponse?.let { cityRepo.saveFavouriteCity(_state.value.apiResponse!!) }
        }
    }

    private fun handleOfflineMode() {
        viewModelScope.launch {
            val dao = cityRepo.getAllSavedCities()
            _state.update { state ->
                state.copy(
                    eventName = if (dao.isEmpty()) WeatherViewModelEvent.OfflineMode else WeatherViewModelEvent.CachedCity,
                    cachedCity = if (dao.isEmpty()) null else dao[0],
                )
            }
        }
    }

    fun getSavedCities() {
        viewModelScope.launch {
            val savedCities = cityRepo.getAllSavedCities()
            _state.update { state ->
                state.copy(
                    eventName = WeatherViewModelEvent.SavedCities,
                    savedCitiesList = savedCities,
                )
            }
        }
    }

    private fun whichCityToShow() {

    }
}

data class WeatherState(
    val eventName: WeatherViewModelEvent = WeatherViewModelEvent.None,
    val apiResponse: com.example.core_domain.domain.model.CityInfo? = null,
    val savedCity: Boolean = false,
    val cachedCity: com.example.core_domain.domain.model.CityInfo? = null,
    val cachedCityName: String = "",
    val cityName: String = "",
    val searchedCity: String = "",
    val dateTime: Int? = null,
    val currentTemp: Double = 0.0,
    val realFeel: Double = 0.0,
    val weatherIcon: String = "",
    val windSpeed: Double? = 0.0,
    val humidity: Int? = 0,
    val pressure: Int? = 0,
    val forecastList: List<com.example.core_domain.domain.model.ForecastInfo> = listOf(),
    val airPollutionDetails: List<com.example.core_domain.domain.data.dto.airPollution.AirPollutionDetailsDto> = listOf(),
    val message: String = "",
    val savedCitiesList: List<com.example.core_domain.domain.model.CityInfo> = listOf(),
    val errorCode: Int = 0,
    val errorMessage: String? = "",
    val cameFromSavedCities: Boolean = false,
)

sealed class WeatherViewModelEvent() {
    object None : WeatherViewModelEvent()
    object Loading : WeatherViewModelEvent()
    object Success : WeatherViewModelEvent()
    object Fail : WeatherViewModelEvent()
    object OfflineMode : WeatherViewModelEvent()
    object NetworkException : WeatherViewModelEvent()
    object ApiError : WeatherViewModelEvent()
    object EmptyFavouriteCity : WeatherViewModelEvent()
    object SavedFavouriteCity : WeatherViewModelEvent()
    object NotSavedFavouriteCity : WeatherViewModelEvent()
    object SavedCities : WeatherViewModelEvent()
    object NoCachedCity : WeatherViewModelEvent()
    object CachedCity : WeatherViewModelEvent()
    object GotCity : WeatherViewModelEvent()
    object GotForecast : WeatherViewModelEvent()
    object GotPollution : WeatherViewModelEvent()
    object GotSearchedCity : WeatherViewModelEvent()
}
