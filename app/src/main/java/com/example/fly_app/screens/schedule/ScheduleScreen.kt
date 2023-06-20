import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter

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
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Time", fontWeight = FontWeight.Bold)
                            Text("Flight", fontWeight = FontWeight.Bold)
                            Text("Destination", fontWeight = FontWeight.Bold)
                            Text("Status", fontWeight = FontWeight.Bold)
                        }
                    }

                    items(visibleRows) { scheduleData ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            val arrTime = scheduleData.arr_time_utc?.format(DateTimeFormatter.ofPattern("HH:mm")).orEmpty()
                            val arrEstimated = scheduleData.arr_estimated_utc?.format(DateTimeFormatter.ofPattern("HH:mm")).orEmpty()

                            val annotatedString = buildAnnotatedString {
                                if (arrTime != arrEstimated) {
                                    withStyle(
                                        style = SpanStyle(
                                            textDecoration = TextDecoration.LineThrough,
                                            color = Color.Red
                                        )
                                    ) {
                                        append(arrEstimated)
                                    }
                                } else {
                                    withStyle(style = SpanStyle(color = Color.Black)) {
                                        append(arrTime)
                                    }
                                }
                            }

                            Text(
                                text = annotatedString,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = arrTime,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = scheduleData.flight_iata.orEmpty(),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = scheduleData.arr_icao.orEmpty(),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = scheduleData.status.orEmpty(),
                                modifier = Modifier.weight(1f)
                            )
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




