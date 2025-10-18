package com.example.practica1moviles22200122.presentation.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.practica1moviles22200122.navigation.Screen


@Composable
fun MenuScreen(navController: NavHostController) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Menú Principal")

            Button(onClick = { navController.navigate(Screen.Water.route) }) {
                Text("Calculadora de consumo de agua")
            }
            Button(onClick = { navController.navigate(Screen.Activity.route) }) {
                Text("Registro de actividad física")
            }
            Button(onClick = { navController.navigate(Screen.Cars.route) }) {
                Text("Catálogo de autos deportivos")
            }
        }
    }
}
