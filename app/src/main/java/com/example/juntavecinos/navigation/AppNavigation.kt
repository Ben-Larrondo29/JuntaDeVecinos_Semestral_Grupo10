package com.example.juntavecinos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juntavecinos.model.AppDatabase
import com.example.juntavecinos.repository.AdminRepository
import com.example.juntavecinos.repository.AvisoRepository
import com.example.juntavecinos.repository.MovimientoRepository
import com.example.juntavecinos.repository.ReservaRepository
import com.example.juntavecinos.repository.VecinoRepository
import com.example.juntavecinos.ui.screens.AvisosScreen
import com.example.juntavecinos.ui.screens.CrearAvisoScreen
import com.example.juntavecinos.ui.screens.GestionReservasScreen
import com.example.juntavecinos.ui.screens.HomeScreen
import com.example.juntavecinos.ui.screens.Ingresos
import com.example.juntavecinos.ui.screens.ListaVecinosScreen
import com.example.juntavecinos.ui.screens.LoginScreen
import com.example.juntavecinos.ui.screens.SolicitudArriendo
import com.example.juntavecinos.viewmodel.AvisoViewModel
import com.example.juntavecinos.viewmodel.AvisoViewModelFactory
import com.example.juntavecinos.viewmodel.LoginViewModel
import com.example.juntavecinos.viewmodel.MovimientoViewModel
import com.example.juntavecinos.viewmodel.ReservaViewModel
import com.example.juntavecinos.viewmodel.VecinoViewModel
import com.example.juntavecinos.viewmodel.ViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext

    val db = AppDatabase.getDatabase(context)
    val reservaRepo = ReservaRepository(db.reservaDao())
    val movimientoRepo = MovimientoRepository(db.movimientoDao())
    val vecinoRepo = VecinoRepository(db.vecinoDao())
    val adminRepo = AdminRepository(db.adminDao())
    val avisoRepo = AvisoRepository(db.avisoDao())

    val factory = ViewModelFactory(reservaRepo, movimientoRepo, vecinoRepo, adminRepo)

    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val reservaViewModel: ReservaViewModel = viewModel(factory = factory)
    val movimientoViewModel: MovimientoViewModel = viewModel(factory = factory)
    val vecinoViewModel: VecinoViewModel = viewModel(factory = factory)
    val avisoViewModel: AvisoViewModel = viewModel(factory = AvisoViewModelFactory(avisoRepo))

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                loginViewModel = loginViewModel,
                alIniciarSesionExitoso = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(navController, loginViewModel)
        }
        composable("solicitud_arriendo") {
            SolicitudArriendo(navController, reservaViewModel)
        }
        composable("gestion_reservas") {
            GestionReservasScreen(navController, reservaViewModel)
        }
        composable("ingresos") {
            Ingresos(navController, movimientoViewModel)
        }
        composable("avisos") {
            AvisosScreen(navController, avisoViewModel)
        }
        composable("crear_aviso") {
            CrearAvisoScreen(navController, avisoViewModel)
        }
        composable("lista_vecinos") {
            ListaVecinosScreen(navController, vecinoViewModel)
        }
    }
}