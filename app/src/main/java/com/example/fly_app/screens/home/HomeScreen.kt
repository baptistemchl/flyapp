package com.example.fly_app.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val flightListData by viewModel.flightDataList.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.fetchFlightData()
    }

    LaunchedEffect(key1 = flightListData) {
        isLoading = true
        // Simuler un délai de chargement
        delay(2000)
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Merci Anas",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Bienvenue dans Flight Detector !\n\n" +
                            "Flight Detector est une application qui vous permet de suivre les avions sur la carte en temps réel. " +
                            "Vous pouvez obtenir des informations sur les retards des arrivées et des départs dans tous les aéroports, " +
                            "ainsi que des données détaillées sur les avions en vol, telles que leur type et le nombre d'années de vol.\n\n" +
                            "Nous espérons que vous apprécierez l'expérience !",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Merci de votre soutien et de votre utilisation de Flight Detector !",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    // Afficher le loader pendant le chargement
                    CircularProgressIndicator()
                } else {
                    // Afficher les données une fois chargées
                    Text(
                        text = "Il y a ${flightListData.size} vols sur terre !",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}

