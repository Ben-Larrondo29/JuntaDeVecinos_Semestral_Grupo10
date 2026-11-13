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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.juntavecinos.ui.theme.MoradoFondoSuave
import com.example.juntavecinos.ui.theme.MoradoOscuro
import com.example.juntavecinos.ui.theme.MoradoPrincipal
import com.example.juntavecinos.ui.theme.TopBarColor
import com.example.juntavecinos.viewmodel.AvisoViewModel

@Composable
fun CrearAvisoScreen(
    navController: NavController,
    avisoViewModel: AvisoViewModel
) {
    val estado by avisoViewModel.estadoFormulario.collectAsState()
    val categorias = listOf("Reuniones", "Comunidad", "Seguridad")

    Scaffold(
        topBar = {
            TopBarColor(
                titulo = "Crear Aviso",
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
            Card(
                colors = CardDefaults.cardColors(containerColor = MoradoFondoSuave),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Nuevo Comunicado Oficial",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MoradoOscuro
                    )
                    Text(
                        text = "Se guardará en la base de datos y aparecerá en la vista de los vecinos.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MoradoOscuro
                    )
                }
            }

            OutlinedTextField(
                value = estado.titulo,
                onValueChange = avisoViewModel::alCambiarTitulo,
                label = { Text("Título del aviso *") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Categoría",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = MoradoOscuro
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categorias.forEach { cat ->
                    FilterChip(
                        selected = estado.categoria == cat,
                        onClick = { avisoViewModel.alCambiarCategoria(cat) },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MoradoPrincipal,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            OutlinedTextField(
                value = estado.fecha,
                onValueChange = avisoViewModel::alCambiarFecha,
                label = { Text("Fecha / Horario (ej: 15/11/2026) *") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.descripcion,
                onValueChange = avisoViewModel::alCambiarDescripcion,
                label = { Text("Detalle del comunicado *") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        avisoViewModel.alCambiarImportante(!estado.importante)
                    }
            ) {
                Checkbox(
                    checked = estado.importante,
                    onCheckedChange = avisoViewModel::alCambiarImportante,
                    colors = CheckboxDefaults.colors(checkedColor = MoradoPrincipal)
                )
                Text(
                    text = "Destacar como aviso importante",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MoradoOscuro
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
                    avisoViewModel.guardarAviso {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Publicar Aviso")
            }
        }
    }
}