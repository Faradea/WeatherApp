package com.macgavrina.weatherapp.domain.usecase

import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.reppository.CityRepository
import io.reactivex.Single

//ToDo add interfaces
object CityUseCase {

    private val cityRepository = CityRepository

    fun getAllCities(): Single<List<City>> {
        return cityRepository.getAllCities()
    }
}