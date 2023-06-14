package com.example.fly_app.screens.airport

import AirportData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fly_app.usecases.GetAirportListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AirportViewModel : ViewModel() {
    private val _airportDataList = MutableStateFlow<List<AirportData>>(emptyList())
    val airportDataList: StateFlow<List<AirportData>> = _airportDataList

    private val getAirportListUseCase = GetAirportListUseCase()

    private val ioDispatcher = Dispatchers.IO

    fun fetchAirportData() {
        viewModelScope.launch {
            try {
                val airportData = withContext(ioDispatcher) {
                    getAirportListUseCase.execute()
                }
                _airportDataList.value = airportData
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


