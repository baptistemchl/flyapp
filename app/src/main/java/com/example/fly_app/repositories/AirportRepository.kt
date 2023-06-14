package com.example.fly_app.repositories

import com.example.fly_app.data.model.AirportsListData
import com.example.fly_app.data.service.AirportApiService
import com.example.fly_app.data.service.ApiServiceBuilder
import retrofit2.Retrofit


class AirportRepository {
    private val retrofit: Retrofit = ApiServiceBuilder.retrofit
    private val apiService: AirportApiService = retrofit.create(AirportApiService::class.java)

     suspend fun getAllAirports(apiKey: String): AirportsListData? {
        return try {
            apiService.getAllAirports(apiKey)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
