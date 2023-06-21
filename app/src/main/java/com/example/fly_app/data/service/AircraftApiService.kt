package com.example.fly_app.data.service

import com.example.fly_app.data.model.AircraftData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AircraftApiService {
    @GET("aircrafts/icao24/{icao24}")
    suspend fun executeAircraftRequest(
        @Path("icao24") icao24: String,
        @Query("withRegistrations") withRegistrations: Boolean = true,
        @Query("withImage") withImage: Boolean = true
    ): AircraftData
}

object AircraftApiBuilder {
    private const val BASE_URL = "https://aerodatabox.p.rapidapi.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", "2a91213e18msh147e5422995b7e8p1e5ecfjsn350db658f31b")
                .addHeader("X-RapidAPI-Host", "aerodatabox.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: AircraftApiService = retrofit.create(AircraftApiService::class.java)
}

