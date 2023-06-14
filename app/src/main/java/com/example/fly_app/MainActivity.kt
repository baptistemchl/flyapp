package com.example.fly_app

import AirportScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fly_app.screens.flight.FlightScreen
import com.example.fly_app.screens.fly.FlyScreen
import com.example.fly_app.screens.home.HomeScreen
import com.example.fly_app.ui.BottomNavItem
import com.example.fly_app.ui.theme.Fly_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Fly_appTheme {
                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationComponent(navController = navController) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F5DC) // Utilise la couleur beige comme fond
        ) {
            NavigationGraph(navController = navController)
        }
    }
}


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen()
        }
        composable(BottomNavItem.Fly.screen_route) {
            FlyScreen()
        }
        composable(BottomNavItem.Flight.screen_route) {
            FlightScreen()
        }
        composable(BottomNavItem.Airport.screen_route) {
            AirportScreen()
        }
    }
}

@Composable
fun BottomNavigationComponent(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Fly,
        BottomNavItem.Flight,
        BottomNavItem.Airport,
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.bordeaux),
        contentColor = colorResource(id = R.color.black),
        elevation = 10.dp // Supprime l'ombre en définissant l'élévation à 0dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = if (currentRoute == item.screen_route) {
                            LocalContentColor.current
                        } else {
                            LocalContentColor.current.copy(alpha = 0.4f)
                        }
                    )
                },
                label = {
                    val textColor = if (currentRoute == item.screen_route) {
                        Color(0xFFF5F5DC) /// Couleur grise pour le label sélectionné
                    } else {
                        Color(0xFF808080) //
                        // Couleur beige pour le label non sélectionné
                    }
                    Text(
                        text = item.title,
                        fontSize = 9.sp,
                        color = textColor
                    )
                },
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        popUpTo(navController.graph.startDestinationRoute!!) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


