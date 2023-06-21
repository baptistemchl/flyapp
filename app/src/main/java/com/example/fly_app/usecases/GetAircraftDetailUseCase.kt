package com.example.fly_app.usecases

import com.example.fly_app.data.model.AircraftData
import com.example.fly_app.data.model.AirlineData
import com.example.fly_app.data.model.AirlinesListData
import com.example.fly_app.repositories.AircraftRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GetAircraftDetailUseCase {
    private val aircraftRepository: AircraftRepository = AircraftRepository()

    suspend fun execute(icao24: String): AircraftData? {
        return try {
            val aircraftInfo = aircraftRepository.getAircraftInfo(icao24)
            val decodedAircraftInfo = Json.decodeFromString<AircraftData>(aircraftInfo)
            test(decodedAircraftInfo)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun test(aircraftData: AircraftData): AircraftData {
        return aircraftData
    }
}
