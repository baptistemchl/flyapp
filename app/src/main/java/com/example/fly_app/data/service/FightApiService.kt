package com.example.fly_app.data.service

import com.example.fly_app.data.model.FlightsListData
import retrofit2.http.GET
import retrofit2.http.Query

interface FightApiService {
    @GET("flights")
    suspend fun getAllFlights(@Query("api_key") apiKey: String): FlightsListData
}

