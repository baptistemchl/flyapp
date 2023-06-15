package com.example.fly_app.screens.flight

import FlightData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fly_app.usecases.GetAirportListUseCase
import com.example.fly_app.usecases.GetFlightListUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightViewModel : ViewModel() {

        private val _flightDataList = MutableStateFlow<List<FlightData>>(emptyList())
        val flightDataList: StateFlow<List<FlightData>> = _flightDataList

        private val getFlightListUseCase = GetFlightListUseCase()

        private val ioDispatcher = Dispatchers.IO

        fun fetchFlightData() {
            viewModelScope.launch {
                try {
                    val flightData = withContext(ioDispatcher) {
                        getFlightListUseCase.execute()
                    }
                    _flightDataList.value = flightData
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
}