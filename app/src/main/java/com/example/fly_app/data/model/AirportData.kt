import com.google.gson.annotations.SerializedName

data class AirportData(
    val name: String?,
   @SerializedName("iata_code") val iataCode: String?,
   @SerializedName("icao_code")  val icaoCode: String?,
    val lat: Double?,
    val lng: Double?,
)
