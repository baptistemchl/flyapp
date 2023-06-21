package com.example.fly_app

import AircraftScreen
import AirportScreen
import ScheduleScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fly_app.screens.flight.FlightScreen
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
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F5DC)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                NavigationGraph(navController = navController)
            }

            BottomAppBarComponent(navController = navController)
        }
    }
}



@Composable
fun BottomAppBarComponent(navController: NavHostController) {
    val bottomNavItems = listOf(
        BottomNavItem.Home,
//        BottomNavItem.Fly,
        BottomNavItem.Flight,
        BottomNavItem.Airport
    )

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0xFF800000), // Couleur bordeaux
    ) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        CompositionLocalProvider(LocalContentColor provides Color.White) {
            bottomNavItems.forEach { navItem ->
                val selected = currentRoute == navItem.screen_route
                val iconTint = if (selected) {
                    Color.Black // Icône noire
                } else {
                    Color.Black.copy(alpha = 0.4f) // Icône noire transparente
                }
                val labelColor = if (selected) {
                    Color(0xFF808080)
                    // Couleur grise pour le label sélectionné
                } else {
                    Color(0xFFF5F5DC)
                    // Couleur beige pour le label non sélectionné
                }

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = navItem.icon),
                            contentDescription = navItem.title,
                            tint = iconTint
                        )
                    },
                    label = {
                        Text(
                            text = navItem.title,
                            fontSize = 9.sp,
                            color = labelColor
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(navItem.screen_route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen()
        }
//        composable(BottomNavItem.Fly.screen_route) {
//            FlyScreen()
//        }
        composable(BottomNavItem.Flight.screen_route) {
            FlightScreen(navController = navController)
        }
        composable(BottomNavItem.Airport.screen_route) {
            AirportScreen(navController = navController)
        }
        composable(
            route = "aircraft/{icao24}",
            arguments = listOf(navArgument("icao24") { type = NavType.StringType })
        ) { backStackEntry ->
            val icao24 = backStackEntry.arguments?.getString("icao24")
            AircraftScreen(icao24 = icao24!!)
        }
        composable(
            route = "schedule/{iataCode}",
            arguments = listOf(navArgument("iataCode") { type = NavType.StringType })
        ) { backStackEntry ->
            val iataCode = backStackEntry.arguments?.getString("iataCode")
            ScheduleScreen(iataCode = iataCode!!)
        }
    }
}
