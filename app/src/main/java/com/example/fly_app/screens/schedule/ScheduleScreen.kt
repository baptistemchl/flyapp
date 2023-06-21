import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ScheduleScreen(
    iataCode: String,
    scheduleViewModel: ScheduleViewModel = viewModel()
) {
    val scheduleDataList by scheduleViewModel.scheduleDataList.collectAsState()
    val scheduleDataListByArr by scheduleViewModel.scheduleDataListByArr.collectAsState()
    val isLoading by scheduleViewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = iataCode) {
        scheduleViewModel.fetchScheduleData(iataCode)
        scheduleViewModel.fetchScheduleDataByArr(iataCode)
    }

    var visibleRowCount by remember { mutableStateOf(1) }
    var selectedOption by remember { mutableStateOf("departures") } // Option sélectionnée par défaut

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (selectedOption == "departures") "Départs" else "Arrivées",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownMenuItem(onClick = {
                        selectedOption = "departures"
                        expanded = false
                    }) {
                        Text("Départs")
                    }
                    DropdownMenuItem(onClick = {
                        selectedOption = "arrivals"
                        expanded = false
                    }) {
                        Text("Arrivées")
                    }
                }
            }
        }

        Box(Modifier.weight(1f)) {
            if (isLoading && scheduleDataList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.primary
                )
            } else {
//                Départs de l'aeroport choisis
                if (selectedOption == "departures") {
                    if (scheduleDataList.isNotEmpty()) {
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
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                            }

                            items(visibleRows) { scheduleData ->
                                Column(Modifier.fillMaxWidth()) {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        val sdfInput = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.FRANCE)
                                        val sdfOutput = SimpleDateFormat("HH:mm", Locale.FRANCE)

                                        val arrTime = scheduleData.arr_time_utc?.let { sdfInput.parse(it) }
                                        val formattedArrTime = arrTime?.let { sdfOutput.format(it) }.orEmpty()

                                        val arrEstimatedTime = scheduleData.arr_estimated_utc?.let { sdfInput.parse(it) }
                                        val formattedArrEstimatedTime = arrEstimatedTime?.let { sdfOutput.format(it) }.orEmpty()

                                        val annotatedString = buildAnnotatedString {
                                            if (formattedArrTime != formattedArrEstimatedTime) {
                                                withStyle(
                                                    style = SpanStyle(
                                                        textDecoration = TextDecoration.LineThrough,
                                                        color = Color.Red
                                                    )
                                                ) {
                                                    append(formattedArrEstimatedTime)
                                                }
                                            } else {
                                                withStyle(style = SpanStyle(color = Color.Black)) {
                                                    append(formattedArrTime)
                                                }
                                            }
                                        }

                                        Column(Modifier.weight(1f)) {
                                            Text(text = formattedArrTime)
                                            Text(text = annotatedString)

                                        }

                                        Text(
                                            text = scheduleData.flight_iata.orEmpty(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = scheduleData.arr_iata.orEmpty(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = scheduleData.status.orEmpty(),
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                                }
                            }
                        }
                    } else {
                        Text("No data")
                    }
            } else {
//                Arrivées à l'aeroport choisis
                    if (scheduleDataListByArr.isNotEmpty()) {
                        val visibleRows = scheduleDataListByArr.subList(0, visibleRowCount * 5)

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
                                    Text("Origin", fontWeight = FontWeight.Bold)
                                    Text("Status", fontWeight = FontWeight.Bold)
                                }
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                            }

                            items(visibleRows) { scheduleData ->
                                Column(Modifier.fillMaxWidth()) {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        val sdfInput = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.FRANCE)
                                        val sdfOutput = SimpleDateFormat("HH:mm", Locale.FRANCE)

                                        val arrTime = scheduleData.arr_time_utc?.let { sdfInput.parse(it) }
                                        val formattedArrTime = arrTime?.let { sdfOutput.format(it) }.orEmpty()

                                        val arrEstimatedTime = scheduleData.arr_estimated_utc?.let { sdfInput.parse(it) }
                                        val formattedArrEstimatedTime = arrEstimatedTime?.let { sdfOutput.format(it) }.orEmpty()

                                        val annotatedString = buildAnnotatedString {
                                            if (formattedArrTime != formattedArrEstimatedTime) {
                                                withStyle(
                                                    style = SpanStyle(
                                                        textDecoration = TextDecoration.LineThrough,
                                                        color = Color.Red
                                                    )
                                                ) {
                                                    append(formattedArrEstimatedTime)
                                                }
                                            } else {
                                                withStyle(style = SpanStyle(color = Color.Black)) {
                                                    append(formattedArrTime)
                                                }
                                            }
                                        }

                                        Column(Modifier.weight(1f)) {
                                            Text(text = formattedArrTime)
                                            Text(text = annotatedString)

                                        }

                                        Text(
                                            text = scheduleData.flight_iata.orEmpty(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = scheduleData.dep_iata.orEmpty(),
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = scheduleData.status.orEmpty(),
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                                }
                            }
                        }
                    } else {
                        Text("No data")
                    }
                }
            }
        }

        if (visibleRowCount * 5 < scheduleDataListByArr.size) {
            Button(
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
