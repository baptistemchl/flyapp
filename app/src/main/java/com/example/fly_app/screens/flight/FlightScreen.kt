package com.example.fly_app.screens.flight

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fly_app.screens.other.OtherViewModel

@Composable
fun FlightScreen(viewModel: FlightViewModel = viewModel()) {
    Column {
        Text("flight")
    }
}

@Preview
@Composable
fun FlightScreenPreview() {
    FlightScreen()
}
