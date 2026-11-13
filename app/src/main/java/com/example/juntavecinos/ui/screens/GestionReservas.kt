package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.juntavecinos.ui.theme.MoradoFondoSuave
import com.example.juntavecinos.ui.theme.MoradoOscuro
import com.example.juntavecinos.ui.theme.TopBarColor
import com.example.juntavecinos.viewmodel.ReservaViewModel

@Composable
fun GestionReservasScreen(
    navController: NavController,
    reservaViewModel: ReservaViewModel
) {
    val listaReservas by reservaViewModel.todasLasReservas.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopBarColor(
                titulo = "Gestión de Arriendos",
                mostrarVolver = true,
                alVolver = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaReservas) { reserva ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MoradoFondoSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = reserva.nombreVecino,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MoradoOscuro
                        )
                        Text("RUT: ${reserva.rutVecino}")
                        Text("Fecha: ${reserva.fecha} (${reserva.horaInicio} - ${reserva.horaFin})")
                        Text("Motivo: ${reserva.motivo}")
                        Text(
                            text = "Estado: ${reserva.estado}",
                            fontWeight = FontWeight.SemiBold,
                            color = MoradoOscuro
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    reservaViewModel.cambiarEstadoReserva(reserva, "APROBADA")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Aprobar")
                            }
                            OutlinedButton(
                                onClick = {
                                    reservaViewModel.cambiarEstadoReserva(reserva, "RECHAZADA")
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Rechazar")
                            }
                        }
                    }
                }
            }
        }
    }
}