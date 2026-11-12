package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.juntavecinos.ui.theme.MoradoOscuro
import com.example.juntavecinos.ui.theme.MoradoPrincipal
import com.example.juntavecinos.ui.theme.TopBarColor
import com.example.juntavecinos.viewmodel.ReservaViewModel

@Composable
fun SolicitudArriendo(
    navController: NavController,
    reservaViewModel: ReservaViewModel
) {
    val estado by reservaViewModel.estadoFormulario.collectAsState()

    Scaffold(
        topBar = {
            TopBarColor(
                titulo = "Solicitar Arriendo",
                mostrarVolver = true,
                alVolver = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Datos de la Solicitud",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MoradoOscuro
            )

            OutlinedTextField(
                value = estado.nombreVecino,
                onValueChange = reservaViewModel::alCambiarNombre,
                label = { Text("Nombre del Vecino/a *") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.rutVecino,
                onValueChange = reservaViewModel::alCambiarRut,
                label = { Text("RUT") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.fecha,
                onValueChange = reservaViewModel::alCambiarFecha,
                label = { Text("Fecha (DD/MM/AAAA) *") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = estado.horaInicio,
                    onValueChange = reservaViewModel::alCambiarHoraInicio,
                    label = { Text("Hora Inicio") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = estado.horaFin,
                    onValueChange = reservaViewModel::alCambiarHoraFin,
                    label = { Text("Hora Fin") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = estado.motivo,
                onValueChange = reservaViewModel::alCambiarMotivo,
                label = { Text("Motivo del evento") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        reservaViewModel.alCambiarAceptaReglamento(!estado.aceptaReglamento)
                    }
            ) {
                Checkbox(
                    checked = estado.aceptaReglamento,
                    onCheckedChange = reservaViewModel::alCambiarAceptaReglamento,
                    colors = CheckboxDefaults.colors(checkedColor = MoradoPrincipal)
                )
                Text(
                    text = "Acepto el reglamento de uso de la sede vecinal *",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            estado.errorMensaje?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    reservaViewModel.guardarReserva {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Enviar Solicitud")
            }
        }
    }
}