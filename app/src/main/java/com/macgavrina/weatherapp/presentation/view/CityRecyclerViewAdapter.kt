package com.macgavrina.weatherapp.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.R
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.presentation.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.city_list_card.view.*

class CityRecyclerViewAdapter(inputOnClickListener: OnCityClickListener) :
    RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder>() {

    private var mItems: List<City>? = null
    private val mOnClickListener: OnCityClickListener = inputOnClickListener

    fun setCities(cities: List<City>) {
        this.mItems = cities
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cityNameTV = view.city_name_text
        val cityHumidityTV = view.city_humidity_text
        val cityLocationTV = view.city_location_text
        val cityTemperatureTV = view.city_temperature_text
        val cityCard = view.city_card

        private var mItem: City? = null

        fun setItem(item: City) {
            mItem = item
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CityRecyclerViewAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        // create a new view
        val view = layoutInflater.inflate(R.layout.city_list_card, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val item = mItems?.get(position) ?: return

        holder.cityNameTV.text = item.name
        holder.cityHumidityTV.text = item.humidity.toString()
        //ToDo use string res with placeholders
        holder.cityLocationTV.text = "lat: ${item.coordinates.lat}, lng: ${item.coordinates.lng}"
        holder.cityTemperatureTV.text = item.airTemp.toString()

        holder.cityCard.setOnClickListener {
            mOnClickListener.onItemClick(item)
        }


        holder.setItem(mItems!![position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        if (mItems != null) {
            return mItems!!.size
        }
        return -1
    }

    interface OnCityClickListener {
        fun onItemClick(city: City)
    }

}