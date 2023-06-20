package com.example.fly_app.usecases

import FlightData
import com.example.fly_app.BuildConfig
import com.example.fly_app.data.model.FlightsListData
import com.example.fly_app.repositories.FlightRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFlightListUseCase {
    private val flightRepository = FlightRepository()

    suspend fun execute(): List<FlightData> = withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.AIRPORT_API_KEY
            val flightsList = flightRepository.getAllFlights(apiKey)
            convertToFlightDataList(flightsList)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun convertToFlightDataList(flightsList: FlightsListData?): List<FlightData> {
        return flightsList?.response?.map { flight: FlightData ->
            FlightData(
                lat = flight.lat,
                lng = flight.lng,
            )
        } ?: emptyList()
    }
}



