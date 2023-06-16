package com.example.fly_app.repositories

import com.example.fly_app.data.model.AirlinesListData
import com.example.fly_app.data.model.SchedulesListData
import com.example.fly_app.data.service.AirlineApiService
import com.example.fly_app.data.service.ApiServiceBuilder
import com.example.fly_app.data.service.ScheduleApiService
import retrofit2.Retrofit


class ScheduleRepository {
    private val retrofit: Retrofit = ApiServiceBuilder.retrofit
    private val apiService: ScheduleApiService = retrofit.create(ScheduleApiService::class.java)

    suspend fun getSchedulesByIataCode(apiKey: String, iataCode: String): SchedulesListData? {
        return try {
            apiService.getSchedulesByIataCode(apiKey, iataCode)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

