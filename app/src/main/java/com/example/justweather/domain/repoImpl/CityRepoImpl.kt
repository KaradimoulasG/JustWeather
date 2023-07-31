package com.example.justweather.domain.repoImpl

import com.example.justweather.data.OpenWeatherApi
import com.example.justweather.data.persistence.CityDao
import com.example.justweather.data.repositories.ICityRepo
import com.example.justweather.domain.model.CityInfo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CityRepoImpl(
    private val api: OpenWeatherApi
) : ICityRepo, KoinComponent {
    private val cityDao: CityDao by inject()

    override suspend fun getCityInfo() =
        api.getCityInfo()

    override suspend fun getFiveDayForecast(latitude: Double, longitude: Double) =
        api.getFiveDayForecast(latitude, longitude)

    override suspend fun getAirPollution(latitude: Double, longitude: Double) =
        api.getAirPollution(latitude, longitude)

//    override suspend fun saveFavouriteCity(city: CityInfo) {
//        cityDao.insertFavouriteCity(city)
//    }
//
//    override suspend fun getFavouriteCity(): CityInfo? {
//        val cityName = cityDao.getFavouriteCity().cityName
//        return when (cityName.isEmpty()) {
//            true -> null
//            false -> cityDao.getFavouriteCity()
//        }
//    }
}
