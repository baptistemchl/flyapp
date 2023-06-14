import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fly_app.screens.airport.AirportViewModel

@Composable
fun AirportScreen(airportViewModel: AirportViewModel = viewModel()) {
    val airportDataList by airportViewModel.airportDataList.collectAsState()

    LaunchedEffect(key1 = airportViewModel) {
        airportViewModel.fetchAirportData()
    }

    val visibleAirportCount = remember { mutableStateOf(5) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp) // Réserve l'espace pour la BottomNavigationBar
    ) {
        airportDataList?.let { data ->
            if (data.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Airport Data:")
                    data.subList(0, visibleAirportCount.value).forEach { airport ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text("Name: ${airport.name}")
                                Text("IATA Code: ${airport.iataCode}")
                                Text("ICAO Code: ${airport.icaoCode}")
                                Text("Latitude: ${airport.lat}")
                                Text("Longitude: ${airport.lng}")
                            }
                        }
                    }
                    if (visibleAirportCount.value < data.size) {
                        Button(
                            onClick = {
                                visibleAirportCount.value += 5
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text("Voir plus")
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SpinningIndicator()
                }
            }
        }
    }
}


@Composable
fun SpinningIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Preview
@Composable
fun AirportScreenPreview() {
    AirportScreen()
}
