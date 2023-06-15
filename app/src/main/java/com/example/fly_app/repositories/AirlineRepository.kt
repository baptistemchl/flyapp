package com.example.fly_app.repositories

import com.example.fly_app.data.model.AirlinesListData
import com.example.fly_app.data.service.AirlineApiService
import com.example.fly_app.data.service.ApiServiceBuilder
import retrofit2.Retrofit


class AirlineRepository {
    private val retrofit: Retrofit = ApiServiceBuilder.retrofit
    private val apiService: AirlineApiService = retrofit.create(AirlineApiService::class.java)

     suspend fun getAllAirlines(apiKey: String): AirlinesListData? {
        return try {
            apiService.getAllAirlines(apiKey)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
