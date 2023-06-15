package com.example.fly_app.screens.airline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fly_app.data.model.AirlineData
import com.example.fly_app.usecases.GetAirlineListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AirlineViewModel : ViewModel() {
    private val _airlineDataList = MutableStateFlow<List<AirlineData>>(emptyList())
    val airlineDataList: StateFlow<List<AirlineData>> = _airlineDataList

    private val getAirportListUseCase = GetAirlineListUseCase()

    private val ioDispatcher = Dispatchers.IO

    fun fetchAirlineData() {
        viewModelScope.launch {
            try {
                val airlineData = withContext(ioDispatcher) {
                    getAirportListUseCase.execute()
                }
                _airlineDataList.value = airlineData
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


