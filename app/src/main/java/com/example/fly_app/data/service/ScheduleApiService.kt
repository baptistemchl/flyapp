package com.example.fly_app.data.service

import com.example.fly_app.data.model.SchedulesListData
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApiService {
    @GET("schedules")
    suspend fun getSchedulesByDepIataCode(
        @Query("api_key") apiKey: String,
        @Query("dep_iata") dep_iata: String
    ): SchedulesListData

    @GET("schedules")
    suspend fun getSchedulesByArrIataCode(
        @Query("api_key") apiKey: String,
        @Query("arr_iata") arr_iata: String
    ): SchedulesListData
}


