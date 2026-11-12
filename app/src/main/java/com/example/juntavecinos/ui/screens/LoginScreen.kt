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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.juntavecinos.ui.theme.MoradoOscuro
import com.example.juntavecinos.ui.theme.MoradoPrincipal
import com.example.juntavecinos.ui.theme.TopBarColor
import com.example.juntavecinos.viewmodel.LoginViewModel
import com.example.juntavecinos.viewmodel.RolUsuario
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.juntavecinos.R


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    alIniciarSesionExitoso: () -> Unit
) {
    val estado by loginViewModel.estadoLogin.collectAsState()

    Scaffold(
        topBar = {
            TopBarColor(titulo = "")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Image(
                painter = painterResource(id = R.drawable.logo_juntavecinos_morado),
                contentDescription = "Logo Junta de Vecinos",
                modifier = Modifier
                    .size(130.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "Iniciar Sesión\nJunta de vecinos Zaror\nLos Cerrillos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MoradoOscuro,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = estado.rolSeleccionado == RolUsuario.VECINO,
                    onClick = { loginViewModel.alSeleccionarTipoLogin(RolUsuario.VECINO) },
                    label = { Text("Soy Vecino/a") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MoradoPrincipal,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = estado.rolSeleccionado == RolUsuario.ADMINISTRADOR,
                    onClick = { loginViewModel.alSeleccionarTipoLogin(RolUsuario.ADMINISTRADOR) },
                    label = { Text("Administrador") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MoradoPrincipal,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (estado.rolSeleccionado == RolUsuario.VECINO) {
                OutlinedTextField(
                    value = estado.nombreVecino,
                    onValueChange = loginViewModel::alCambiarNombre,
                    label = { Text("Nombre Completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = estado.rutVecino,
                    onValueChange = loginViewModel::alCambiarRut,
                    label = { Text("RUT (ej: 12.345.678-9)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                OutlinedTextField(
                    value = estado.usuarioAdmin,
                    onValueChange = loginViewModel::alCambiarUsuarioAdmin,
                    label = { Text("Usuario Directiva (admin)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = estado.claveAdmin,
                    onValueChange = loginViewModel::alCambiarClaveAdmin,
                    label = { Text("Contraseña (1234)") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            estado.errorMensaje?.let { mensaje ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    loginViewModel.iniciarSesion {
                        alIniciarSesionExitoso()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Ingresar", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}