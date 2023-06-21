package com.example.fly_app.usecases

import com.example.fly_app.data.model.AircraftData
import com.example.fly_app.repositories.AircraftRepository
class GetAircraftDetailUseCase {
    suspend fun execute(icao24: String): AircraftData? {
        val aircraftRepository = AircraftRepository()
        return try {
            aircraftRepository.getAircraftInfo(icao24)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

