import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AircraftScreen(
    icao24: String,
    aircraftViewModel: AircraftViewModel = viewModel()
) {
    val aircraftDetailData by aircraftViewModel.aircraftInfo.collectAsState()

    LaunchedEffect(key1 = icao24) {
        aircraftViewModel.fetchAircraftInfo(icao24)
    }

    Column(Modifier.fillMaxSize()) {
        Text(text = aircraftDetailData)
    }
}

