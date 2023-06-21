package com.example.fly_app.screens.home

import FlightData
import androidx.lifecycle.ViewModel
import com.example.fly_app.screens.flight.FlightViewModel

import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val flightViewModel = FlightViewModel()
    private val _flightDataList = flightViewModel.flightDataList
    val flightDataList: StateFlow<List<FlightData>> = _flightDataList

    fun fetchFlightData() {
        flightViewModel.fetchFlightData()
    }
}
