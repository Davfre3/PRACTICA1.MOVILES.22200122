package com.example.practica1moviles22200122.presentation.water

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
fun WaterCalculatorScreen(navController: NavHostController) {
    // ✅ Material 3 usa SnackbarHostState en lugar de ScaffoldState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("Masculino") }
    var resultado by remember { mutableStateOf("") }

    val opcionesGenero = listOf("Masculino", "Femenino", "Sin especificar")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Calculadora de consumo de agua") }) },
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

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Peso corporal
            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso corporal (kg)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Género
            Text("Seleccione su género:")
            opcionesGenero.forEach { opcion ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    RadioButton(
                        selected = genero == opcion,
                        onClick = { genero = opcion }
                    )
                    Text(opcion)
                }
            }

            // Botón calcular
            Button(
                onClick = {
                    val pesoVal = peso.toFloatOrNull()

                    when {
                        nombre.isBlank() -> coroutineScope.launch {
                            snackbarHostState.showSnackbar("Ingrese su nombre.")
                        }
                        pesoVal == null -> coroutineScope.launch {
                            snackbarHostState.showSnackbar("Ingrese un número válido para el peso.")
                        }
                        pesoVal < 5 || pesoVal > 200 -> coroutineScope.launch {
                            snackbarHostState.showSnackbar("El peso debe estar entre 5 y 200 kg.")
                        }
                        else -> {
                            val factor = when (genero) {
                                "Masculino" -> 1.02
                                "Femenino" -> 1.01
                                else -> 1.00
                            }
                            val litros = pesoVal * 0.035 * factor
                            resultado = String.format("%.2f", litros)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular consumo diario")
            }

            // Resultado
            if (resultado.isNotEmpty()) {
                Text(
                    text = "$nombre debe beber aproximadamente $resultado litros de agua al día.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón volver al menú
            Button(
                onClick = { navController.popBackStack(Screen.Menu.route, inclusive = false) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Volver al menú principal")
            }
        }
    }
}
