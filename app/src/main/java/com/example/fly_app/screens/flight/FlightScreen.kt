package com.example.fly_app.screens.flight

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fly_app.widgets.LocationPermissionDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FlightScreen(viewModel: FlightViewModel = viewModel()) {
    val navController = rememberNavController()
    val currentLocation by viewModel.currentLatLng.collectAsState()
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 16f)
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

    if (permissionState.hasPermission) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                // Gérer le clic sur la carte
            }
        )
    }
}


@Preview
@Composable
fun FlightScreenPreview() {
    FlightScreen()
}

