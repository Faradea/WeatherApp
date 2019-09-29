package com.macgavrina.weatherapp.presentation.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.macgavrina.weatherapp.LOG_TAG
import com.macgavrina.weatherapp.R
import com.macgavrina.weatherapp.presentation.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import com.macgavrina.weatherapp.data.model.CityWithWeather

//ToDo Use Koin/Dagger

const val CITY_ID_KEY = "cityId"
const val ADD_CITY_ACTIVITY_REQUEST_CODE = 1

class MainActivity : AppCompatActivity(), CityRecyclerViewAdapter.OnCityClickListener {

    private lateinit var viewModel: CitiesViewModel
    private lateinit var citiesRecyclerViewAdapter: CityRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(CitiesViewModel::class.java)

        citiesRecyclerViewAdapter = CityRecyclerViewAdapter(this)
        citiesList.adapter = citiesRecyclerViewAdapter
        citiesList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.getCities().observe(this, Observer { cities ->
            updateCitiesList(cities)
        })

        add_city_button.setOnClickListener {
            startAddCityActivity()
        }
    }

    override fun onItemClick(cityWithWeather: CityWithWeather) {
        Log.d(LOG_TAG, "on city click: city = $cityWithWeather")
        val intent = Intent(this, CityDetailedActivity::class.java)
        intent.putExtra(CITY_ID_KEY, cityWithWeather.city.uid)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != ADD_CITY_ACTIVITY_REQUEST_CODE) return
        if (resultCode != Activity.RESULT_OK) return

        viewModel.newCityWasAdded()
    }

    private fun updateCitiesList(cities: List<CityWithWeather>) {
        Log.d(LOG_TAG, "cities list is changed, update UI with cities list, length = ${cities.size}")
        citiesRecyclerViewAdapter.setCities(cities)
    }

    private fun startAddCityActivity() {
        val intent = Intent(this, AddCityActivity::class.java)
        startActivityForResult(intent, ADD_CITY_ACTIVITY_REQUEST_CODE)
    }
}
