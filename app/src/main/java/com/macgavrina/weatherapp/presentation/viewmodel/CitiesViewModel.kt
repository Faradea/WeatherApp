package com.macgavrina.weatherapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macgavrina.weatherapp.LOG_TAG
import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.domain.usecase.CityUseCase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CitiesViewModel(application: Application) : AndroidViewModel(MainApplication.instance) {

    private val compositeDisposable = CompositeDisposable()
    private var cities = MutableLiveData<List<City>>()

    init {
        loadCities()
    }

    fun getCities(): LiveData<List<City>> {
        return cities
    }

    private fun loadCities() {
        compositeDisposable.add(
            CityUseCase.getAllCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { cities ->
                    this.cities.value = cities
                    cities.forEach { city ->
                        getWeatherForCity(city)
                    }
                })
    }

    private fun getWeatherForCity(city: City) {
        compositeDisposable.add(
            CityUseCase.getWeatherForCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { weatherForCity ->
                    Log.d(LOG_TAG, "Weather for city with id = ${city.uid} is received from server, $weatherForCity")
                    val citiesList = cities.value?.toMutableList()
                    var i = 0
                    citiesList?.forEach { cityFromList ->
                        if (cityFromList.uid == city.uid) {
                            citiesList[i].humidity = weatherForCity.main.humidity
                            citiesList[i].airTemp = weatherForCity.main.temp
                        }
                        i += 1
                    }
                    this.cities.postValue(citiesList)
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}