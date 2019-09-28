package com.macgavrina.weatherapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.macgavrina.weatherapp.LOG_TAG
import com.macgavrina.weatherapp.R
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.presentation.viewmodel.CitiesViewModel
import com.macgavrina.weatherapp.presentation.viewmodel.CityDetailsViewModel
import kotlinx.android.synthetic.main.activity_city_detailed.*

class CityDetailedActivity : AppCompatActivity() {

    private lateinit var viewModel: CityDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detailed)

        viewModel = ViewModelProvider(this).get(CityDetailsViewModel::class.java)

        val selectedCityId = intent.getIntExtra(CITY_ID_KEY, -1)
        Log.d(LOG_TAG, "Selected city id from intent = $selectedCityId")
        if (selectedCityId != -1) {
            viewModel.setSelectedCityId(selectedCityId)
        }

        viewModel.getSelectedCity().observe(this, Observer { city ->
            Log.d(LOG_TAG, "Selected city: uid = ${city.uid}, name = ${city.name}")
            displayCityDetails(city)
        })
    }

    private fun displayCityDetails(city: City) {
        city_detailed_name.text = city.name
        city_detailed_temperature.text = city.airTemp.toString()
        city_detailed_humidity.text = "${city.humidity} %"
    }
}
