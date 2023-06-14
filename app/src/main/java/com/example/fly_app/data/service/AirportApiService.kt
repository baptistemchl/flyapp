package com.example.fly_app.data.service

import com.example.fly_app.data.model.AirportsListData
import retrofit2.http.GET
import retrofit2.http.Query

interface AirportApiService {
    @GET("airports")
    suspend fun getAllAirports(@Query("api_key") apiKey: String): AirportsListData
}

