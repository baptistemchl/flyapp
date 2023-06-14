package com.example.fly_app.usecases

import AirportData
import com.example.fly_app.BuildConfig
import com.example.fly_app.data.model.AirportsListData
import com.example.fly_app.repositories.AirportRepository

class GetAirportListUseCase {
    private val airportRepository = AirportRepository()
    suspend fun execute(): List<AirportData> {
        return try {
            val apiKey = BuildConfig.AIRPORT_API_KEY
            val airportsList = airportRepository.getAllAirports(apiKey)
            convertToAirportDataList(airportsList)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun convertToAirportDataList(airportsList: AirportsListData?): List<AirportData> {
        return airportsList?.response?.map { airport: AirportData ->
            AirportData(
                name = airport.name,
                iataCode = airport.iataCode,
                icaoCode = airport.icaoCode,
                lat = airport.lat,
                lng = airport.lng
            )
        } ?: emptyList()
    }
}


