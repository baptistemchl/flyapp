import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScheduleScreen(
    iataCode: String,
    scheduleViewModel: ScheduleViewModel = viewModel()
) {
    val scheduleDataList by scheduleViewModel.scheduleDataList.collectAsState()
    val isLoading by scheduleViewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = iataCode) {
        scheduleViewModel.fetchScheduleData(iataCode)
    }

    var visibleRowCount by remember { mutableStateOf(1) }

    Column(Modifier.fillMaxSize()) {
        Text("Schedule Data for $iataCode")

        Box(Modifier.weight(1f)) {
            if (isLoading && scheduleDataList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.primary
                )
            } else if (scheduleDataList.isNotEmpty()) {
                val visibleRows = scheduleDataList.subList(0, visibleRowCount * 5)

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(visibleRows) { scheduleData ->
                        Card(
                            modifier = Modifier.padding(vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = scheduleData.flight_iata,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                // Add other information related to the flight
                            }
                        }
                    }
                }
            } else {
                Text("No data")
            }
        }

        if (visibleRowCount * 5 < scheduleDataList.size) {
            androidx.compose.material3.Button(
                onClick = { visibleRowCount++ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Voir plus")
            }
        }
    }
}


