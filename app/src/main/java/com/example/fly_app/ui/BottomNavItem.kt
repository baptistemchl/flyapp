package com.example.fly_app.ui

import com.example.fly_app.R


sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object Fly : BottomNavItem("Fly", R.drawable.ic_fly, "fly")
    object Flight : BottomNavItem("Flight", R.drawable.ic_flight, "flight")
    object Other : BottomNavItem("Other", R.drawable.ic_other,"other")
    object Map : BottomNavItem("Map", R.drawable.ic_other,"map")
}