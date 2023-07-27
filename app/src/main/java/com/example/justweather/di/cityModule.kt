package com.example.justweather.di

import com.example.justweather.data.repositories.ICityRepo
import com.example.justweather.domain.repoImpl.CityRepoImpl
import com.example.justweather.domain.useCases.GetForecastUseCase
import com.example.justweather.presentation.TestingViewModel
import org.koin.dsl.module

val cityModule = module {

    factory<ICityRepo> { CityRepoImpl(get()) }

    factory { TestingViewModel(get(), get()) }

    single { GetForecastUseCase(get()) }
}
