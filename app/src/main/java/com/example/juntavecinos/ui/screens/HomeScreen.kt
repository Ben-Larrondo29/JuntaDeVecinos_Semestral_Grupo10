package com.example.juntavecinos.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.juntavecinos.R
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    fun navegarDesdeMenu(ruta: String) {
        scope.launch {
            drawerState.close()
            navController.navigate(ruta) {
                launchSingleTop = true
            }
        }
    }

    val itemColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MoradoFondoSuave,
        selectedTextColor = MoradoOscuro,
        selectedIconColor = MoradoPrincipal,
        unselectedTextColor = Color.DarkGray,
        unselectedIconColor = Color.Gray
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
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
                    selected = rutaActual == "home",
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
                        selected = rutaActual == "ingresos",
                        onClick = { navegarDesdeMenu("ingresos") },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.AddCircle, contentDescription = null) },
                        label = { Text("Crear Aviso") },
                        selected = rutaActual == "crear_aviso",
                        onClick = { navegarDesdeMenu("crear_aviso") },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        label = { Text("Arriendos") },
                        selected = rutaActual == "gestion_reservas",
                        onClick = { navegarDesdeMenu("gestion_reservas") },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Vecinos") },
                        selected = rutaActual == "lista_vecinos",
                        onClick = { navegarDesdeMenu("lista_vecinos") },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                } else {
                    // OPCIONES EXCLUSIVAS DEL VECINO
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                        label = { Text("Avisos y Comunicados") },
                        selected = rutaActual == "avisos",
                        onClick = { navegarDesdeMenu("avisos") },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        label = { Text("Solicitar Arriendo de Sede") },
                        selected = rutaActual == "solicitud_arriendo",
                        onClick = { navegarDesdeMenu("solicitud_arriendo") },
                        colors = itemColors,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                // Espacio blanco central aprovechado para mostrar el logo
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_juntavecinos_morado),
                        contentDescription = "Logo Junta de Vecinos Población Zaror",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .heightIn(max = 170.dp)
                    )
                }

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
                        scope.launch {
                            drawerState.close()
                            loginViewModel.cerrarSesion()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
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
                                "Desliza desde el borde izquierdo o toca el menú (☰) para gestionar los módulos."
                            } else {
                                "Desliza desde el borde izquierdo o toca el menú (☰) para navegar."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MoradoOscuro
                        )
                    }
                }

                if (esAdmin) {
                    TarjetaInformativa(
                        icono = Icons.AutoMirrored.Filled.List,
                        titulo = "Control de Tesorería",
                        descripcion = "Registra ingresos por cuotas o arriendos y mantén al día el balance de gastos de la directiva."
                    )
                    TarjetaInformativa(
                        icono = Icons.Default.AddCircle,
                        titulo = "Publicación de Avisos",
                        descripcion = "Difunde reuniones, operativos comunitarios y alertas de seguridad directamente a los vecinos."
                    )
                    TarjetaInformativa(
                        icono = Icons.Default.DateRange,
                        titulo = "Gestión de Arriendos",
                        descripcion = "Revisa, aprueba o rechaza las solicitudes de uso de la sede vecinal."
                    )
                    TarjetaInformativa(
                        icono = Icons.Default.Person,
                        titulo = "Vecinos",
                        descripcion = "Consulta y busca el listado completo de vecinos registrados en Población Zaror."
                    )
                } else {
                    TarjetaInformativa(
                        icono = Icons.Default.Notifications,
                        titulo = "Comunicados Oficiales",
                        descripcion = "Mantente informado sobre asambleas, operativos de limpieza y novedades del sector."
                    )
                    TarjetaInformativa(
                        icono = Icons.Default.DateRange,
                        titulo = "Arriendo de Sede Vecinal",
                        descripcion = "Envía tu solicitud indicando fecha, horario y motivo para reservar el espacio comunitario."
                    )
                    TarjetaInformativa(
                        icono = Icons.Default.Info,
                        titulo = "Junta de Vecinos Población Zaror",
                        descripcion = "Recuerda enviar tus solicitudes de arriendo con al menos 5 días de anticipación."
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaInformativa(
    icono: ImageVector,
    titulo: String,
    descripcion: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                color = MoradoFondoSuave,
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = MoradoPrincipal,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(26.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MoradoOscuro
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }
        }
    }
}