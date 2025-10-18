package com.example.practica1moviles22200122.navigation

sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object Water : Screen("water")
    object Activity : Screen("activity")
    object Cars : Screen("cars")
}
