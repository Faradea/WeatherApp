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
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.presentation.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent

//ToDo strings from xml to res
//ToDo check all code with Lint
//ToDo Use Koin/Dagger

const val CITY_ID_KEY = "cityId"

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
    }

    override fun onItemClick(city: City) {
        Log.d(LOG_TAG, "on city click: city = $city")
        val intent = Intent(this, CityDetailedActivity::class.java)
        intent.putExtra(CITY_ID_KEY, city.uid)
        startActivity(intent)
    }

    private fun updateCitiesList(cities: List<City>) {
        Log.d(LOG_TAG, "cities list is changed, update UI with cities list, length = ${cities.size}")
        citiesRecyclerViewAdapter.setCities(cities)
    }
}
