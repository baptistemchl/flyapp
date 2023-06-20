package com.example.fly_app.data.model

data class ScheduleData(
    val flight_iata: String?,
    val flight_icao: String,
    val cs_flight_iata: String?,
    val status: String?,
    val cs_airline_iata: String?,
    val arr_time_utc: String?,
    val arr_estimated_utc: String?,
    val dep_iata: String,
    val dep_icao: String,
    val arr_iata: String,
    val arr_icao: String,


)
