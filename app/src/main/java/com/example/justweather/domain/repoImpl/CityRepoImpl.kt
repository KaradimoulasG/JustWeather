package com.example.justweather.domain.repoImpl

import com.example.justweather.data.OpenWeatherApi
import com.example.justweather.data.persistence.CityDao
import com.example.justweather.data.repositories.ICityRepo
import com.example.justweather.domain.model.CityInfo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CityRepoImpl(
    private val api: OpenWeatherApi,
    private val cityDao: CityDao,
) : ICityRepo, KoinComponent {
//    private val cityDao: CityDao by inject()

    override suspend fun getCityInfo(cityName: String) =
        api.getCityInfo(cityName)

    override suspend fun getFiveDayForecast(latitude: Double, longitude: Double) =
        api.getFiveDayForecast(latitude, longitude)

    override suspend fun getAirPollution(latitude: Double, longitude: Double) =
        api.getAirPollution(latitude, longitude)

    override suspend fun saveFavouriteCity(city: CityInfo) {
        cityDao.insertFavouriteCity(city)
        timber.log.Timber.i("PAOK here")
    }

    override suspend fun getFavouriteCity(): CityInfo? {
        val cityName = cityDao.getFavouriteCity()
        return when (cityName.cityName.isNullOrBlank()) {
            true -> null
            false -> cityDao.getFavouriteCity()
        }
    }
}
