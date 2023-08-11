package com.example.justweather.data.repositories

import com.example.justweather.data.dto.airPollution.AirPollutionDto
import com.example.justweather.data.dto.cityInfo.CityInfoDto
import com.example.justweather.data.dto.forecast.ForecastDto
import com.example.justweather.data.networkResultHandling.NetworkResult
import com.example.justweather.domain.model.CityInfo

interface ICityRepo {

    suspend fun getCityInfo(cityName: String): NetworkResult<CityInfoDto>
    suspend fun getFiveDayForecast(latitude: Double, longitude: Double): NetworkResult<ForecastDto>
    suspend fun getAirPollution(latitude: Double, longitude: Double): NetworkResult<AirPollutionDto>

    suspend fun saveFavouriteCity(city: CityInfo)
    suspend fun getFavouriteCity(): CityInfo
    suspend fun getAllSavedCities(): List<CityInfo>
}
