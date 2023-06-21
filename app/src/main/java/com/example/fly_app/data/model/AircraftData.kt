package com.example.fly_app.data.model


data class AircraftData(
    val airlineName : String?,
    val numSeats : Int?,
    val firstFlightDate:String?,
    val deliveryDate:String?,
    val typeName:String?,
    val numEngines:Int?,
    val engineType:String?,
    val productionLine:String?,
    val ageYears:Double?,
    val image:AircraftPictureData?,
    val hexIcao:String?,
)