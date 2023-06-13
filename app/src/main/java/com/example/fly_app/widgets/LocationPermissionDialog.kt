package com.example.fly_app.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LocationPermissionDialog(
    showDialog: Boolean,
    onPermissionRequested: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Permission de localisation") },
            text = { Text("Cette application a besoin de votre permission pour accéder à votre localisation.") },
            confirmButton = {
                Button(
                    onClick = onPermissionRequested
                ) {
                    Text("Accepter")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss

                ) {
                    Text("Refuser")
                }
            }
        )
    }
}
