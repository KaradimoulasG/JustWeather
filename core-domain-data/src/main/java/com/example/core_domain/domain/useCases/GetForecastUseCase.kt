package com.example.core_domain.domain.useCases

import com.example.core_domain.domain.data.repositories.ICityRepo

class GetForecastUseCase(
    private val cityRepo: ICityRepo,
) {

    suspend operator fun invoke(lat: Double, lon: Double) =
        cityRepo.getFiveDayForecast(lat, lon)
}
