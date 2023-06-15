package com.example.fly_app.repositories

import com.example.fly_app.data.model.FlightsListData
import com.example.fly_app.data.service.ApiServiceBuilder
import com.example.fly_app.data.service.FightApiService
import retrofit2.Retrofit


class FlightRepository {
    private val retrofit: Retrofit = ApiServiceBuilder.retrofit
    private val apiService: FightApiService = retrofit.create(FightApiService::class.java)

     suspend fun getAllFlights(apiKey: String): FlightsListData? {
        return try {
            apiService.getAllFlights(apiKey)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
