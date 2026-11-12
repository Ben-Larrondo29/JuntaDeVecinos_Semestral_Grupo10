package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.juntavecinos.viewmodel.ReservaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudArriendo(
    navController: NavController,
    viewModel: ReservaViewModel
) {
    val estado by viewModel.estadoFormulario.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("SOLICITUD DE ARRIENDO") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = estado.nombreVecino,
                onValueChange = viewModel::alCambiarNombre,
                label = { Text("Nombre Completo:") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.rutVecino,
                onValueChange = viewModel::alCambiarRut,
                label = { Text("RUT del Vecino/a:") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.fecha,
                onValueChange = viewModel::alCambiarFecha,
                label = { Text("Fecha:") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = estado.horaInicio,
                    onValueChange = viewModel::alCambiarHoraInicio,
                    label = { Text("Hora Inicio:") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = estado.horaFin,
                    onValueChange = viewModel::alCambiarHoraFin,
                    label = { Text("Hora Fin:") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = estado.motivo,
                onValueChange = viewModel::alCambiarMotivo,
                label = { Text("Motivo / Evento:") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = estado.aceptaReglamento,
                    onCheckedChange = viewModel::alCambiarAceptaReglamento
                )
                Text("Acepto el reglamento de uso de la sede")
            }

            estado.errorMensaje?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    viewModel.guardarReserva {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar Solicitud")
            }
        }
    }
}