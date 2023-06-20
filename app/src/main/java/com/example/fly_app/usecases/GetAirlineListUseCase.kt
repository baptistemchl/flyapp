package com.example.fly_app.usecases

import com.example.fly_app.BuildConfig
import com.example.fly_app.data.model.AirlineData
import com.example.fly_app.data.model.AirlinesListData
import com.example.fly_app.repositories.AirlineRepository

class GetAirlineListUseCase {
    private val airlineRepository = AirlineRepository()
    suspend fun execute(): List<AirlineData> {
        return try {
            val apiKey = BuildConfig.AIRPORT_API_KEY
            val airlinesList = airlineRepository.getAllAirlines(apiKey)
            convertToAirportDataList(airlinesList)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun convertToAirportDataList(airlinesList: AirlinesListData?): List<AirlineData> {
        return airlinesList?.response?.map { airline: AirlineData ->
            AirlineData(
                name = airline.name,
                iata_code = airline.iata_code,
                icao_code = airline.icao_code,
            )
        } ?: emptyList()
    }
}


