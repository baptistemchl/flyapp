package com.example.fly_app.data.service

import com.example.fly_app.data.model.AirlinesListData
import com.example.fly_app.data.model.AirportsListData
import com.example.fly_app.data.model.SchedulesListData
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApiService {
    @GET("schedules")
    suspend fun getSchedulesByIataCode(
        @Query("api_key") apiKey: String,
        @Query("iata_code") iataCode: String
    ): SchedulesListData
}


