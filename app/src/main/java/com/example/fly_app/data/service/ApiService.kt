package com.example.fly_app.data.service
import com.example.fly_app.data.model.PingResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("ping")
    suspend fun ping(@Query("api_key") apiKey: String): PingResponse
}

object ApiServiceBuilder {
    private const val BASE_URL = "https://airlabs.co/api/v9/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
