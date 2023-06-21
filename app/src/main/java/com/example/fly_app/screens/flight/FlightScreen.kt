package com.example.fly_app.screens.flight

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fly_app.R
import com.example.fly_app.ui.BottomNavItem
import com.example.fly_app.widgets.LocationPermissionDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FlightScreen(flightViewModel: FlightViewModel = viewModel(), navController: NavController) {
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
                        areaPoints.forEach { point ->
                            val airplaneIcon = getAirplaneIcon()
                            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(airplaneIcon)
                            Marker(
                                state = MarkerState(position = point),
                                icon = bitmapDescriptor,
                                onClick = { marker ->
                                    val index = areaPoints.indexOf(point)
                                    val icao = flightListData?.getOrNull(index)?.hex ?: ""
                                    navController?.navigate("aircraft/${icao}")
                                    true
                                }
                            )
                        }
                    }
                }
            }
        }
        RefreshButton(
            onClick = {
                areaPoints.clear()
                flightViewModel.fetchFlightData()
            },
            icon = painterResource(R.drawable.ic_refresh),
            contentDescription = "Refresh",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun getAirplaneIcon(): Bitmap {
    val vectorDrawable = AppCompatResources.getDrawable(LocalContext.current, R.drawable.ic_flight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable!!.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
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

@Composable
fun RefreshButton(
    onClick: () -> Unit,
    icon: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        shape = CircleShape,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = LocalContentColor.current
            )
        }
    }
}
