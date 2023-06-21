package com.example.fly_app.data.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class AircraftApiService {
    private val client = OkHttpClient()

    suspend fun executeAircraftRequest(icao24: String): String {
        val url = "https://aerodatabox.p.rapidapi.com/aircrafts/icao24/$icao24?withRegistrations=true&withImage=true"
        val request = buildRequest(url)
        val response = performRequest(request)
        return response.body?.string() ?: ""
    }

    private fun buildRequest(url: String): Request {
        return Request.Builder()
            .url(url)
            .get()
            .addHeader("X-RapidAPI-Key", "2a91213e18msh147e5422995b7e8p1e5ecfjsn350db658f31b")
            .addHeader("X-RapidAPI-Host", "aerodatabox.p.rapidapi.com")
            .build()
    }

    private suspend fun performRequest(request: Request): okhttp3.Response {
        return withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
    }
}
