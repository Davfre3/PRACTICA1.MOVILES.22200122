package com.example.practica1moviles22200122.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practica1moviles22200122.presentation.menu.MenuScreen
import com.example.practica1moviles22200122.presentation.water.WaterCalculatorScreen
import com.example.practica1moviles22200122.presentation.activity.PhysicalActivityScreen
import com.example.practica1moviles22200122.presentation.cars.CarsCatalogScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(Screen.Menu.route)     { MenuScreen(navController) }
        composable(Screen.Water.route)    { WaterCalculatorScreen(navController) }
        composable(Screen.Activity.route) { PhysicalActivityScreen(navController) }
        composable(Screen.Cars.route)     { CarsCatalogScreen(navController) }
    }
}
