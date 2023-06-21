package com.example.fly_app.usecases

import com.example.fly_app.repositories.AircraftRepository

class GetAircraftDetailUseCase {
    private val aircraftRepository: AircraftRepository = AircraftRepository()

    suspend fun execute(icao24: String): String {
        return try {
            aircraftRepository.getAircraftInfo(icao24)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
