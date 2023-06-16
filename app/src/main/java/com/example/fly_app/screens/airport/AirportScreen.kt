import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fly_app.R
import com.example.fly_app.screens.airport.AirportViewModel

@Composable
fun AirportScreen(airportViewModel: AirportViewModel = viewModel(), navController: NavController,) {
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
                        .padding(top = 40.dp) // Ajoute une marge supérieure pour éviter le chevauchement
                ) {
                    data.subList(0, visibleAirportCount.value).forEach { airport ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = Color.White)
                                .clickable {
                                    navController.navigate("schedule/${airport.iataCode}")
                                }
                                .padding(16.dp)
                        ) {
                            airport.name?.let {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_airport),
                                        contentDescription = "Airport Icon",
                                        tint = Color(0xFF888888) // Couleur de l'icône
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${airport.name}",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF333333) // Couleur du texte du nom de l'aéroport
                                        ),
                                        modifier = Modifier.weight(1f) // Permet de centrer le texte avec l'icône
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("IATA Code:")
                                    }
                                    append(" ${airport.iataCode}")
                                },
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xFF666666) // Couleur du texte du code IATA
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("ICAO Code:")
                                    }
                                    append(" ${airport.icaoCode}")
                                },
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xFF666666) // Couleur du texte du code ICAO
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Latitude:")
                                    }
                                    append(" ${airport.lat}")
                                },
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xFF666666) // Couleur du texte de la latitude
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Longitude:")
                                    }
                                    append(" ${airport.lng}")
                                },
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color(0xFF666666) // Couleur du texte de la longitude
                                )
                            )
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
        Surface(
            modifier = Modifier

                .fillMaxWidth()
                .align(Alignment.TopCenter),
            color = Color(0xFFFFF3E0), // Couleur de fond beige
            elevation = 4.dp, // Niveau d'élévation pour l'ombre

        ) {
            Text(
                text = "Airport Data",
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333) // Couleur du texte de l'en-tête
                )
            )
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


