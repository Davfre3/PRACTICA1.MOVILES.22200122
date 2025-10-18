package com.example.practica1moviles22200122.presentation.cars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.practica1moviles22200122.navigation.Screen
import com.example.practica1moviles22200122.data.mock.autosDeportivosMock
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsCatalogScreen(navController: NavHostController) {
    val autos = autosDeportivosMock
    val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US)
    val total = autos.sumOf { it.precio }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Catálogo de Autos Deportivos") }) },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(autos) { auto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = auto.imagenUrl,
                                contentDescription = auto.modelo,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(end = 16.dp)
                            )
                            Column {
                                Text(
                                    text = "${auto.marca} ${auto.modelo}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Precio: ${formatoMoneda.format(auto.precio)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Costo total de todos los autos: ${formatoMoneda.format(total)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

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
