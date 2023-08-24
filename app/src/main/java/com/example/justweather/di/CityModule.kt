package com.example.justweather.di

import com.example.core_domain.domain.data.repositories.ICityRepo
import com.example.justweather.presentation.WeatherViewModel
import org.koin.dsl.module

val CityModule = module {

    single<ICityRepo> { com.example.core_domain.domain.repoImpl.CityRepoImpl(get(), get()) }

    single { WeatherViewModel(get(), get()) }

    single { com.example.core_domain.domain.useCases.GetForecastUseCase(get()) }
}
