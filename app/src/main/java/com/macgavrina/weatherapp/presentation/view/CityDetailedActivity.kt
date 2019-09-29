package com.macgavrina.weatherapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.macgavrina.weatherapp.LOG_TAG
import com.macgavrina.weatherapp.R
import com.macgavrina.weatherapp.data.model.CityWithWeather
import com.macgavrina.weatherapp.presentation.viewmodel.CityDetailsViewModel
import com.macgavrina.weatherapp.utils.DateFormatter
import kotlinx.android.synthetic.main.activity_city_detailed.*

class CityDetailedActivity : AppCompatActivity() {

    private lateinit var viewModel: CityDetailsViewModel
    private lateinit var forecastRecyclerViewAdapter: HourlyForecastViewAdapter

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
            Log.d(LOG_TAG, "Selected city: uid = ${city.city.uid}, name = ${city.city.name}")
            displayCityDetails(city)
        })


        forecastRecyclerViewAdapter = HourlyForecastViewAdapter()
        city_detailed_hourly_forecast_recyclerview.adapter = forecastRecyclerViewAdapter
        city_detailed_hourly_forecast_recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.getForecast().observe(this, Observer { forecast ->
            forecastRecyclerViewAdapter.setForecast(forecast)
        })
    }

    private fun displayCityDetails(city: CityWithWeather) {
        city_detailed_name.text = city.city.name
        if (city.weatherForCity?.dt != null) {
            city_detailed_datetime.text = DateFormatter.formatDateAndTimeFromTimestamp(city.weatherForCity!!.dt)
        }
        if (city.weatherForCity?.main != null) {
            city_detailed_temperature.text = resources.getString(R.string.temperature, city.weatherForCity?.main?.temp.toString())
        }
        if (city.weatherForCity?.main?.humidity != null) {
            city_detailed_humidity.text = "${city.weatherForCity?.main?.humidity} %"
        }
    }
}
