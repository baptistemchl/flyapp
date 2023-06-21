import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.fly_app.R


@Composable
fun AircraftScreen(
    icao24: String,
    aircraftViewModel: AircraftViewModel = viewModel()
) {
    val aircraftDetailData by aircraftViewModel.aircraftInfo.collectAsState()

    LaunchedEffect(key1 = icao24) {
        aircraftViewModel.fetchAircraftInfo(icao24)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val painter = rememberImagePainter(
                data = aircraftDetailData?.image?.url,
                builder = {
                    error(R.drawable.plane)
                }
            )

            Image(
                painter = if (aircraftDetailData?.image?.url.isNullOrEmpty()) painterResource(R.drawable.plane) else painter,
                contentDescription = "Image de couverture de l'aéronef",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop
            )

            if (painter.state is ImagePainter.State.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)

                )
            }

            // Informations sur l'aéronef
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Compagnie : " + aircraftDetailData?.airlineName.orEmpty(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Type : " + aircraftDetailData?.typeName.orEmpty().toString(),
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Production : " + aircraftDetailData?.productionLine.orEmpty(),
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Age : " + aircraftDetailData?.ageYears.toString() + " années",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Premier vol : " + aircraftDetailData?.firstFlightDate.orEmpty(),
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Date de livraison : " + aircraftDetailData?.deliveryDate.orEmpty(),
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Type de moteur : " + aircraftDetailData?.engineType.orEmpty(),
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Nombre de sièges : " + aircraftDetailData?.numSeats.toString() ,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}


