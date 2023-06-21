package com.example.fly_app.repositories

import com.example.fly_app.data.model.AircraftData
import com.example.fly_app.data.service.AircraftApiBuilder
import com.example.fly_app.data.service.AircraftApiService
import retrofit2.Retrofit

class AircraftRepository {
    private val retrofit: Retrofit = AircraftApiBuilder.retrofit
    private val apiService: AircraftApiService = retrofit.create(AircraftApiService::class.java)

    suspend fun getAircraftInfo(icao24: String): AircraftData {
        return apiService.executeAircraftRequest(icao24)
    }
}


