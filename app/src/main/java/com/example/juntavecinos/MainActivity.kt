package com.example.juntavecinos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juntavecinos.ui.screens.LoginScreen
import com.example.juntavecinos.ui.screens.Registro
import com.example.juntavecinos.ui.theme.JuntaVecinosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuntaVecinosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {
                        // Ruta de Login
                        composable("login") {
                            LoginScreen(
                                onNavigateToRegister = {
                                    navController.navigate("register")
                                },
                                onLoginSuccess = {
                                    Toast.makeText(this@MainActivity, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                                    // Aquí puedes navegar a tu pantalla principal (ej: navController.navigate("home"))
                                }
                            )
                        }

                        // Ruta de Registro
                        composable("register") {
                            Registro(
                                onNavigateBack = {
                                    navController.popBackStack() // Vuelve al login
                                },
                                onRegisterSuccess = {
                                    Toast.makeText(this@MainActivity, "¡Cuenta creada con éxito!", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack() // Regresa al login tras registrarse
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}