package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.juntavecinos.ui.theme.MoradoFondoSuave
import com.example.juntavecinos.ui.theme.MoradoOscuro
import com.example.juntavecinos.ui.theme.MoradoPrincipal
import com.example.juntavecinos.ui.theme.TopBarColor
import com.example.juntavecinos.viewmodel.MovimientoViewModel

@Composable
fun Ingresos(
    navController: NavController,
    movimientoViewModel: MovimientoViewModel
) {
    val estado by movimientoViewModel.estadoFormulario.collectAsState()
    val movimientos by movimientoViewModel.todosLosMovimientos.collectAsState(initial = emptyList())
    val totalIngresos by movimientoViewModel.totalIngresos.collectAsState(initial = 0.0)
    val totalGastos by movimientoViewModel.totalGastos.collectAsState(initial = 0.0)
    val saldo = (totalIngresos ?: 0.0) - (totalGastos ?: 0.0)

    Scaffold(
        topBar = {
            TopBarColor(
                titulo = "Tesorería",
                mostrarVolver = true,
                alVolver = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MoradoFondoSuave),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Balance Actual: $${saldo.toInt()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MoradoOscuro
                    )
                    Text("Ingresos: $${(totalIngresos ?: 0.0).toInt()}  |  Gastos: $${(totalGastos ?: 0.0).toInt()}")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = estado.tipo == "INGRESO",
                    onClick = { movimientoViewModel.alCambiarTipo("INGRESO") },
                    label = { Text("Ingreso (+)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MoradoPrincipal,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = estado.tipo == "GASTO",
                    onClick = { movimientoViewModel.alCambiarTipo("GASTO") },
                    label = { Text("Gasto (-)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MoradoPrincipal,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = estado.concepto,
                onValueChange = movimientoViewModel::alCambiarConcepto,
                label = { Text("Concepto") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = estado.monto,
                    onValueChange = movimientoViewModel::alCambiarMonto,
                    label = { Text("Monto ($)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = estado.fecha,
                    onValueChange = movimientoViewModel::alCambiarFecha,
                    label = { Text("Fecha") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = { movimientoViewModel.guardarMovimiento {} },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Registrar Movimiento")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(movimientos) { mov ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(mov.concepto, fontWeight = FontWeight.SemiBold, color = MoradoOscuro)
                                Text("${mov.tipo} • ${mov.fecha}", style = MaterialTheme.typography.bodySmall)
                            }
                            Text(
                                text = "$${mov.monto.toInt()}",
                                fontWeight = FontWeight.Bold,
                                color = MoradoPrincipal
                            )
                        }
                    }
                }
            }
        }
    }
}