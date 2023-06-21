package com.example.fly_app.repositories

import com.example.fly_app.data.service.AircraftApiService

class AircraftRepository {

    private val apiService: AircraftApiService = AircraftApiService()

    suspend fun getAircraftInfo(icao24: String): String {
        return apiService.executeAircraftRequest(icao24)
    }
}

