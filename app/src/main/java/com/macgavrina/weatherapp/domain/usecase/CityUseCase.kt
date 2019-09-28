package com.macgavrina.weatherapp.domain.usecase

import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.model.WeatherForCity
import com.macgavrina.weatherapp.data.reppository.CityRepository
import io.reactivex.Maybe
import io.reactivex.Single

//ToDo add interfaces
object CityUseCase {

    private val cityRepository = CityRepository

    fun getAllCities(): Single<List<City>> {
        return cityRepository.getAllCities()
    }

    fun getCityDetails(cityId: Int): Maybe<City> {
        return cityRepository.getCityById(cityId)
    }

    fun getWeatherForCity(city: City): Single<WeatherForCity> {
        return cityRepository.getWeatherForCity(city)
    }
}