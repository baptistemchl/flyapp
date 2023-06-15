package com.example.fly_app.screens.flight

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fly_app.widgets.LocationPermissionDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState




@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FlightScreen(flightViewModel: FlightViewModel = viewModel()) {
    val navController = rememberNavController()
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val flightListData by flightViewModel.flightDataList.collectAsState()
    val visibleFlightCount = remember { mutableStateOf(150) }
    val areaPoints = remember { mutableStateListOf<LatLng>() }

    LaunchedEffect(key1 = flightViewModel) {
        flightViewModel.fetchFlightData()
    }

    LocationPermissionDialog(
        showDialog = !permissionState.hasPermission,
        onPermissionRequested = {
            permissionState.launchPermissionRequest()
        },
        onDismiss = {
            navController.popBackStack()
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        if (flightListData == null) {
            SpinningIndicator()
        } else {
            if (permissionState.hasPermission) {
                areaPoints.clear()

                flightListData?.let { data ->
                    if (data.isNotEmpty()) {
                        data.subList(0, visibleFlightCount.value).forEach { flight ->
                            val point = flight.lat?.let { flight.lng?.let { it1 -> LatLng(it, it1) } }
                            if (point != null) {
                                areaPoints.add(point)
                            }
                        }
                    }
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (areaPoints.isNotEmpty()) {
                        areaPoints.forEach {
                            Marker(state = MarkerState(position = it))
                        }
                    }
                }

                Button(
                    onClick = {
                        areaPoints.clear()
                        flightViewModel.fetchFlightData()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Refresh")
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




