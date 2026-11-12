package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.juntavecinos.viewmodel.LoginViewModel
import com.example.juntavecinos.viewmodel.RolUsuario
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val estadoLogin by loginViewModel.estadoLogin.collectAsState()
    val esAdmin = estadoLogin.rolActivo == RolUsuario.ADMINISTRADOR

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val itemColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MoradoFondoSuave,
        selectedTextColor = MoradoOscuro,
        selectedIconColor = MoradoPrincipal,
        unselectedTextColor = Color.DarkGray,
        unselectedIconColor = Color.Gray
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {
                // Cabecera morada diferenciada por rol
                Surface(
                    color = MoradoPrincipal,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Población Zaror",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        if (esAdmin) {
                            Text(
                                text = "Directiva / Administrador",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MoradoFondoSuave
                            )
                        } else {
                            Text(
                                text = estadoLogin.nombreVecino,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MoradoFondoSuave
                            )
                            if (estadoLogin.rutVecino.isNotBlank()) {
                                Text(
                                    text = "RUT: ${estadoLogin.rutVecino}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MoradoFondoSuave
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Opción común de Inicio
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = {
                        scope.launch { drawerState.close() }
                    },
                    colors = itemColors,
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                if (esAdmin) {
                    // OPCIONES EXCLUSIVAS DEL ADMINISTRADOR
                    NavigationDrawerItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Tesorería") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("ingresos")
                        },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.AddCircle, contentDescription = null) },
                        label = { Text("Crear Aviso") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("crear_aviso")
                        },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        label = { Text("Arriendos") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("gestion_reservas")
                        },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                } else {
                    // OPCIONES EXCLUSIVAS DEL VECINO
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                        label = { Text("Avisos y Comunicados") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("avisos")
                        },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        label = { Text("Solicitar Arriendo de Sede") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("solicitud_arriendo")
                        },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    },
                    label = {
                        Text(
                            text = "Cerrar Sesión",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        loginViewModel.cerrarSesion()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBarColor(
                    titulo = "Población Zaror",
                    iconoNavegacion = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MoradoFondoSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (esAdmin) "RESUMEN ADMINISTRATIVO" else "Bienvenido/a, ${estadoLogin.nombreVecino}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MoradoOscuro
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (esAdmin) {
                                "Gestiona tesorería, avisos y arriendos desde el menú lateral."
                            } else {
                                "Abre el menú lateral para revisar avisos o solicitar la sede."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MoradoOscuro
                        )
                    }
                }

            }
        }
    }
}