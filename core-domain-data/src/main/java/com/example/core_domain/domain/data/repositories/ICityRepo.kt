package com.example.core_domain.domain.data.repositories

import com.example.core_domain.domain.data.dto.airPollution.AirPollutionDto
import com.example.core_domain.domain.data.dto.cityInfo.CityInfoDto
import com.example.core_domain.domain.data.dto.forecast.ForecastDto
import com.example.core_domain.domain.data.networkResultHandling.NetworkResult
import com.example.core_domain.domain.model.CityInfo

interface ICityRepo {

    suspend fun getCityInfo(cityName: String): NetworkResult<CityInfoDto>
    suspend fun getFiveDayForecast(latitude: Double, longitude: Double): NetworkResult<ForecastDto>
    suspend fun getAirPollution(latitude: Double, longitude: Double): NetworkResult<AirPollutionDto>
    suspend fun saveFavouriteCity(city: CityInfo)
    suspend fun getFavouriteCity(): CityInfo
    suspend fun getAllSavedCities(): List<CityInfo>
    suspend fun checkIfCityIsSaved(name: String): Boolean
}
