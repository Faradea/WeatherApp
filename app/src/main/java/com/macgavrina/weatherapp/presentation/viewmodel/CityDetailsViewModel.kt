package com.macgavrina.weatherapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.domain.usecase.CityUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CityDetailsViewModel(application: Application) : AndroidViewModel(MainApplication.instance) {

    private val compositeDisposable = CompositeDisposable()
    private var selectedCity = MutableLiveData<City>()

    fun getSelectedCity(): LiveData<City> {
        return selectedCity
    }

    fun setSelectedCityId(cityId: Int) {
        loadCityDetails(cityId)
    }

    private fun loadCityDetails(cityId: Int) {
        compositeDisposable.add(
            CityUseCase.getCityDetails(cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { cityDetails->
                    this.selectedCity.value = cityDetails
                })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}