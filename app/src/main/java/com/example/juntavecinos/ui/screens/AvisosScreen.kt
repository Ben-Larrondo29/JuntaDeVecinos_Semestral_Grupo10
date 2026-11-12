package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.DateRange
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

private data class AvisoVecinal(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val fecha: String,
    val descripcion: String,
    val importante: Boolean = false
)

@Composable
fun AvisosScreen(
    navController: NavController
) {
    val categorias = listOf("Todos", "Reuniones", "Comunidad", "Seguridad")
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    val avisos = remember {
        listOf(
            AvisoVecinal(
                id = 1,
                titulo = "Asamblea General Ordinaria",
                categoria = "Reuniones",
                fecha = "15/11/2026 • 19:00 hrs",
                descripcion = "Se convoca a todos los socios y vecinos de Población Zaror a la reunión mensual en la sede vecinal. Se revisará el balance de tesorería y proyectos comunitarios.",
                importante = true
            ),
            AvisoVecinal(
                id = 2,
                titulo = "Operativo de Limpieza y Áreas Verdes",
                categoria = "Comunidad",
                fecha = "18/11/2026 • 10:00 hrs",
                descripcion = "Jornada comunitaria de limpieza en la plaza central del sector. Contaremos con batea municipal para retiro de cachureos hasta las 14:00 hrs."
            ),
            AvisoVecinal(
                id = 3,
                titulo = "Actualización de Alarmas Comunitarias",
                categoria = "Seguridad",
                fecha = "10/11/2026",
                descripcion = "Recordamos a los vecinos mantener actualizados sus números de contacto en el padrón de la directiva para el sistema de alerta vecinal.",
                importante = true
            ),
            AvisoVecinal(
                id = 4,
                titulo = "Horarios de Arriendo de Sede",
                categoria = "Comunidad",
                fecha = "05/11/2026",
                descripcion = "Las solicitudes de arriendo deben enviarse con al menos 5 días de anticipación desde la aplicación, aceptando el reglamento vigente."
            )
        )
    }

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
            // Encabezado informativo
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
                        imageVector = Icons.Default.Campaign,
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

            // Filtros por categoría
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

            // Listado de avisos
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