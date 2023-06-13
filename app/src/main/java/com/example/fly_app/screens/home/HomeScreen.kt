package com.example.fly_app.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fly_app.screens.other.OtherViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    Column {
        Text("home")
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
