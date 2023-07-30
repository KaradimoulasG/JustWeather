package com.example.justweather.domain.repoImpl

import com.example.justweather.data.OpenWeatherApi
import com.example.justweather.data.persistence.CityDao
import com.example.justweather.data.repositories.ICityRepo

class CityRepoImpl(
    private val api: OpenWeatherApi,
    private val cityDao: CityDao,
) : ICityRepo {

    override suspend fun getCityInfo() =
        api.getCityInfo()

    override suspend fun getFiveDayForecast(latitude: Double, longitude: Double) =
        api.getFiveDayForecast(latitude, longitude)

    override suspend fun getAirPollution(latitude: Double, longitude: Double) =
        api.getAirPollution(latitude, longitude)
}
