package com.example.practica1moviles22200122.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.practica1moviles22200122.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicalActivityScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var actividad by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }
    var intensidad by remember { mutableStateOf("Media") }
    var resultado by remember { mutableStateOf("") }

    val actividades = listOf("Correr", "Caminar", "Nadar", "Ciclismo", "Yoga")
    val caloriasPorMinuto = mapOf(
        "Correr" to 10,
        "Caminar" to 5,
        "Nadar" to 8,
        "Ciclismo" to 7,
        "Yoga" to 4
    )

    val intensidades = listOf("Baja", "Media", "Alta")

    var expanded by remember { mutableStateOf(false) }

    // ✅ Scaffold correcto para Material3
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Actividad Física") })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Actividad (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = actividad,
                    onValueChange = {},
                    label = { Text("Tipo de actividad") },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    actividades.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                actividad = opcion
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Duración
            OutlinedTextField(
                value = duracion,
                onValueChange = { duracion = it },
                label = { Text("Duración (minutos)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Intensidad
            Text("Seleccione la intensidad:")
            intensidades.forEach { opcion ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    RadioButton(
                        selected = intensidad == opcion,
                        onClick = { intensidad = opcion }
                    )
                    Text(opcion)
                }
            }

            // Botón calcular
            Button(
                onClick = {
                    val duracionVal = duracion.toIntOrNull()

                    when {
                        actividad.isBlank() -> coroutineScope.launch {
                            snackbarHostState.showSnackbar("Seleccione una actividad.")
                        }
                        duracionVal == null -> coroutineScope.launch {
                            snackbarHostState.showSnackbar("Ingrese un número válido para la duración.")
                        }
                        duracionVal <= 0 -> coroutineScope.launch {
                            snackbarHostState.showSnackbar("La duración debe ser positiva.")
                        }
                        else -> {
                            val baseCal = caloriasPorMinuto[actividad] ?: 0
                            val factor = when (intensidad) {
                                "Baja" -> 0.8
                                "Media" -> 1.0
                                "Alta" -> 1.2
                                else -> 1.0
                            }
                            val total = baseCal * duracionVal * factor
                            resultado = String.format("%.2f", total)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular calorías quemadas")
            }

            // Resultado
            if (resultado.isNotEmpty()) {
                Text(
                    text = "Has quemado aproximadamente $resultado calorías.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Botón volver
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.popBackStack(Screen.Menu.route, inclusive = false) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Volver al menú principal")
            }
        }
    }
}
