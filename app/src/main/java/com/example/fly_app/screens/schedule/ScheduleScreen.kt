import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScheduleScreen(
    iataCode: String,
    scheduleViewModel: ScheduleViewModel = viewModel()
) {
    val scheduleDataList by scheduleViewModel.scheduleDataList.collectAsState()

    LaunchedEffect(key1 = iataCode) {
        scheduleViewModel.fetchScheduleData(iataCode)
    }

    Column {
        Text("Schedule Data for $iataCode")

        if (scheduleDataList.isNotEmpty()) {
            scheduleDataList.forEach { scheduleData ->
                Text(scheduleData.flight_iata)
            }
        } else {
            Text("No data")
        }
    }
}
