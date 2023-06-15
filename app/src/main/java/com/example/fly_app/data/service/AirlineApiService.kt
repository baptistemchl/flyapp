package com.example.fly_app.data.service

import com.example.fly_app.data.model.AirlinesListData
import com.example.fly_app.data.model.AirportsListData
import retrofit2.http.GET
import retrofit2.http.Query

interface AirlineApiService {
    @GET("airline")
    suspend fun getAllAirlines(@Query("api_key") apiKey: String): AirlinesListData
}

