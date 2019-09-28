package com.macgavrina.weatherapp.data.reppository

import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.data.model.City
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

object CityRepository {

    private val cityDAO = MainApplication.db.cityDAO()
    private var allCities = cityDAO.getAll()

    fun getAllCities(): Single<List<City>> {
        return cityDAO.getAll()
    }

    fun addCity(city: City): Completable {
        return cityDAO.insertCity(city)
    }

    fun getCityById(cityId: Int): Maybe<City> {
        return cityDAO.getCity(cityId)
    }
}