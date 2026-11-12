package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.layout.*
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
import com.example.juntavecinos.ui.theme.TopBarColor
import com.example.juntavecinos.viewmodel.LoginViewModel
import com.example.juntavecinos.viewmodel.RolUsuario

@Composable
fun HomeScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val estadoLogin by loginViewModel.estadoLogin.collectAsState()

    Scaffold(
        topBar = {
            TopBarColor(
                titulo = "Población Zaror",
                acciones = {
                    TextButton(
                        onClick = {
                            loginViewModel.cerrarSesion()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Text("Cerrar Sesión")
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
            val saludo = if (estadoLogin.rolActivo == RolUsuario.ADMINISTRADOR) {
                "Panel de Administración"
            } else {
                "Bienvenido/a, ${estadoLogin.nombreVecino}"
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MoradoFondoSuave),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = saludo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MoradoOscuro,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ACCESOS DEL VECINO
            if (estadoLogin.rolActivo == RolUsuario.VECINO) {
                Button(
                    onClick = { navController.navigate("solicitud_arriendo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Solicitar Arriendo de Sede")
                }
            }

            // ACCESOS DEL ADMINISTRADOR
            if (estadoLogin.rolActivo == RolUsuario.ADMINISTRADOR) {
                Button(
                    onClick = { navController.navigate("gestion_reservas") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Gestionar Reservas")
                }

                Button(
                    onClick = { navController.navigate("ingresos") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Tesorería (Ingresos y Gastos)")
                }
            }
        }
    }
}