import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fly_app.screens.airport.AirportViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun AirportScreen(airportViewModel: AirportViewModel = viewModel()) {
    val airportDataList by airportViewModel.airportDataList.collectAsState()

    LaunchedEffect(key1 = airportViewModel) {
        airportViewModel.fetchAirportData()
    }

    Column {
        airportDataList?.let { data ->
            if (data.isNotEmpty()) {
                Text("Airport Data:")
                data.take(10).forEach { airport ->
                    Text("Name: ${airport.name}")
                    Text("IATA Code: ${airport.iataCode}")
                    Text("ICAO Code: ${airport.icaoCode}")
                    Text("Latitude: ${airport.lat}")
                    Text("Longitude: ${airport.lng}")
                    Text("-------------------------------")
                }
            } else {
                Text("No airport data available.")
            }
        } ?: Text("Loading...")
    }
}

@Preview
@Composable
fun AirportScreenPreview() {
    AirportScreen()
}
