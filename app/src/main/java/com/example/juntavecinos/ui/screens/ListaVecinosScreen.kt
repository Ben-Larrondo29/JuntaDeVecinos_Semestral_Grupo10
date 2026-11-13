package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.juntavecinos.model.Vecino
import com.example.juntavecinos.ui.theme.MoradoFondoSuave
import com.example.juntavecinos.ui.theme.MoradoOscuro
import com.example.juntavecinos.ui.theme.MoradoPrincipal
import com.example.juntavecinos.ui.theme.TopBarColor
import com.example.juntavecinos.viewmodel.VecinoViewModel

@Composable
fun ListaVecinosScreen(
    navController: NavController,
    vecinoViewModel: VecinoViewModel
) {
    val vecinos by vecinoViewModel.todosLosVecinos.collectAsState(initial = emptyList())
    val busqueda by vecinoViewModel.textoBusqueda.collectAsState()
    val errorFormulario by vecinoViewModel.errorFormulario.collectAsState()

    var mostrarDialogoAgregar by remember { mutableStateOf(false) }
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevoRut by remember { mutableStateOf("") }

    var vecinoAEliminar by remember { mutableStateOf<Vecino?>(null) }

    val vecinosFiltrados = if (busqueda.isBlank()) {
        vecinos
    } else {
        vecinos.filter { vecino ->
            vecino.nombre.contains(busqueda, ignoreCase = true) ||
                    vecino.rut.contains(busqueda, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopBarColor(
                titulo = "Vecinos de la Población",
                mostrarVolver = true,
                alVolver = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    nuevoNombre = ""
                    nuevoRut = ""
                    vecinoViewModel.limpiarErrorFormulario()
                    mostrarDialogoAgregar = true
                },
                containerColor = MoradoPrincipal,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar vecino"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tarjeta resumen
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
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MoradoPrincipal,
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text(
                            text = "Vecinos Población Zaror",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MoradoOscuro
                        )
                        Text(
                            text = "${vecinosFiltrados.size} vecino(s) con acceso habilitado",
                            style = MaterialTheme.typography.bodySmall,
                            color = MoradoOscuro
                        )
                    }
                }
            }

            // Buscador por nombre o RUT
            OutlinedTextField(
                value = busqueda,
                onValueChange = vecinoViewModel::alCambiarBusqueda,
                label = { Text("Buscar por nombre o RUT") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MoradoPrincipal
                    )
                },
                trailingIcon = {
                    if (busqueda.isNotEmpty()) {
                        IconButton(onClick = { vecinoViewModel.alCambiarBusqueda("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Limpiar búsqueda"
                            )
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Listado de vecinos
            if (vecinosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (busqueda.isBlank()) {
                            "Aún no hay vecinos registrados. Toca '+' para agregar uno."
                        } else {
                            "No se encontraron vecinos con \"$busqueda\"."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = vecinosFiltrados,
                        key = { it.rut }
                    ) { vecino ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Surface(
                                    color = MoradoPrincipal,
                                    shape = CircleShape,
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = vecino.nombre.firstOrNull()?.uppercase() ?: "V",
                                            color = Color.White,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = vecino.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MoradoOscuro
                                    )
                                    Text(
                                        text = "RUT: ${vecino.rut}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.DarkGray
                                    )
                                }
                                IconButton(
                                    onClick = { vecinoAEliminar = vecino }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar vecino",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Agregar vecino
        if (mostrarDialogoAgregar) {
            AlertDialog(
                onDismissRequest = {
                    mostrarDialogoAgregar = false
                    vecinoViewModel.limpiarErrorFormulario()
                },
                title = {
                    Text(
                        text = "Registrar Nuevo Vecino",
                        fontWeight = FontWeight.Bold,
                        color = MoradoOscuro
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = nuevoNombre,
                            onValueChange = {
                                nuevoNombre = it
                                vecinoViewModel.limpiarErrorFormulario()
                            },
                            label = { Text("Nombre completo") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = nuevoRut,
                            onValueChange = {
                                nuevoRut = it
                                vecinoViewModel.limpiarErrorFormulario()
                            },
                            label = { Text("RUT (ej. 12.345.678-9)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        errorFormulario?.let { mensajeError ->
                            Text(
                                text = mensajeError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            vecinoViewModel.agregarVecino(
                                rut = nuevoRut,
                                nombre = nuevoNombre,
                                alGuardarExitoso = {
                                    mostrarDialogoAgregar = false
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MoradoPrincipal)
                    ) {
                        Text("Guardar", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            mostrarDialogoAgregar = false
                            vecinoViewModel.limpiarErrorFormulario()
                        }
                    ) {
                        Text("Cancelar", color = Color.DarkGray)
                    }
                }
            )
        }

        // Diálogo de confirmación para eliminar vecino
        vecinoAEliminar?.let { vecino ->
            AlertDialog(
                onDismissRequest = { vecinoAEliminar = null },
                title = {
                    Text(
                        text = "Eliminar Vecino",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                text = {
                    Text(
                        text = "¿Deseas eliminar a ${vecino.nombre} (RUT: ${vecino.rut}) del padrón? Al eliminarlo, ya no podrá iniciar sesión en la aplicación."
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            vecinoViewModel.eliminarVecino(vecino)
                            vecinoAEliminar = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Eliminar", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { vecinoAEliminar = null }) {
                        Text("Cancelar", color = Color.DarkGray)
                    }
                }
            )
        }
    }
}