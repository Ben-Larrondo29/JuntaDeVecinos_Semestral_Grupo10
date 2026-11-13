package com.example.juntavecinos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.juntavecinos.R
import com.example.juntavecinos.ui.theme.MoradoFondoSuave
import com.example.juntavecinos.ui.theme.MoradoOscuro
import com.example.juntavecinos.ui.theme.MoradoPrincipal
import com.example.juntavecinos.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    alIniciarSesionExitoso: () -> Unit
) {
    val estadoLogin by loginViewModel.estadoLogin.collectAsState()

    var esModoAdmin by remember { mutableStateOf(false) }
    var rutVecino by remember { mutableStateOf("") }
    var usuarioAdmin by remember { mutableStateOf("") }
    var claveAdmin by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_juntavecinos_morado),
                contentDescription = "Logo Junta de Vecinos Población Zaror",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(130.dp)
                    .padding(bottom = 12.dp)
            )

            Text(
                text = "Junta de Vecinos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MoradoOscuro
            )
            Text(
                text = "Población Zaror",
                style = MaterialTheme.typography.titleMedium,
                color = MoradoPrincipal
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de rol: Vecino / Administrador
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = !esModoAdmin,
                    onClick = {
                        esModoAdmin = false
                        loginViewModel.limpiarError()
                    },
                    label = {
                        Text(
                            text = "Soy Vecino",
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MoradoPrincipal,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )

                FilterChip(
                    selected = esModoAdmin,
                    onClick = {
                        esModoAdmin = true
                        loginViewModel.limpiarError()
                    },
                    label = {
                        Text(
                            text = "Directiva / Admin",
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MoradoPrincipal,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (!esModoAdmin) {
                OutlinedTextField(
                    value = rutVecino,
                    onValueChange = {
                        rutVecino = it
                        loginViewModel.limpiarError()
                    },
                    label = { Text("RUT del Vecino (ej. 12.345.678-9)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MoradoPrincipal
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                OutlinedTextField(
                    value = usuarioAdmin,
                    onValueChange = {
                        usuarioAdmin = it
                        loginViewModel.limpiarError()
                    },
                    label = { Text("Usuario Administrador") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MoradoPrincipal
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = claveAdmin,
                    onValueChange = {
                        claveAdmin = it
                        loginViewModel.limpiarError()
                    },
                    label = { Text("Contraseña") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MoradoPrincipal
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Mensaje de error si el RUT no está registrado o las credenciales fallan
            estadoLogin.errorMensaje?.let { mensaje ->
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!esModoAdmin) {
                        loginViewModel.iniciarSesionVecino(
                            rut = rutVecino,
                            alExito = alIniciarSesionExitoso
                        )
                    } else {
                        loginViewModel.iniciarSesionAdmin(
                            usuario = usuarioAdmin,
                            clave = claveAdmin,
                            alExito = alIniciarSesionExitoso
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MoradoPrincipal),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Ingresar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            if (!esModoAdmin) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MoradoFondoSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Solo pueden acceder los vecinos previamente inscritos en el padrón de la Población Zaror.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MoradoOscuro,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}