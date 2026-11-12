package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.juntavecinos.ui.theme.MoradoFondoSuave
import com.example.juntavecinos.ui.theme.MoradoOscuro
import com.example.juntavecinos.ui.theme.MoradoPrincipal
import com.example.juntavecinos.ui.theme.MoradoSecundario
import com.example.juntavecinos.ui.theme.TopBarColor
import com.example.juntavecinos.viewmodel.AvisoViewModel

@Composable
fun AvisosScreen(
    navController: NavController,
    avisoViewModel: AvisoViewModel
) {
    val categorias = listOf("Todos", "Reuniones", "Comunidad", "Seguridad")
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    val avisos by avisoViewModel.todosLosAvisos.collectAsState(initial = emptyList())

    val avisosFiltrados = if (categoriaSeleccionada == "Todos") {
        avisos
    } else {
        avisos.filter { it.categoria == categoriaSeleccionada }
    }

    Scaffold(
        topBar = {
            TopBarColor(
                titulo = "Avisos y Comunicados",
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MoradoFondoSuave),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = MoradoPrincipal,
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text(
                            text = "Tablón Oficial Directiva",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MoradoOscuro
                        )
                        Text(
                            text = "${avisosFiltrados.size} comunicado(s) disponible(s)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MoradoOscuro
                        )
                    }
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categorias) { categoria ->
                    FilterChip(
                        selected = categoriaSeleccionada == categoria,
                        onClick = { categoriaSeleccionada = categoria },
                        label = { Text(categoria) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MoradoPrincipal,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            if (avisosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay avisos publicados en esta categoría.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(avisosFiltrados) { aviso ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (aviso.importante) MoradoFondoSuave else Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = MoradoSecundario,
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Text(
                                            text = aviso.categoria,
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }

                                    if (aviso.importante) {
                                        Text(
                                            text = "★ Importante",
                                            color = MoradoPrincipal,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Text(
                                    text = aviso.titulo,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MoradoOscuro
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = null,
                                        tint = MoradoPrincipal,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = aviso.fecha,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MoradoOscuro
                                    )
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    color = MoradoPrincipal.copy(alpha = 0.2f)
                                )

                                Text(
                                    text = aviso.descripcion,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}