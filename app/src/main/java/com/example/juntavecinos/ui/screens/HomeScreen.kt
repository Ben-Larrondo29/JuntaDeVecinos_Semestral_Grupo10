package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("JUNTA DE VECINOS POBLACIÓN ZAROR") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Gestión Comunitaria",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = { navController.navigate("solicitud_arriendo") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Solicitar Arriendo de Sede (Vecino/a)")
            }

            Button(
                onClick = { navController.navigate("gestion_reservas") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Aprobar/Gestionar Reservas (Secretaría)")
            }

            Button(
                onClick = { navController.navigate("transparencia") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Transparencia Financiera (Público)")
            }
        }
    }
}