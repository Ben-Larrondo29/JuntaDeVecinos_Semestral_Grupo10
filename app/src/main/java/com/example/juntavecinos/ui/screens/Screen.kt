package com.example.juntavecinos.ui.screens

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SolicitudArriendo : Screen("solicitud_arriendo")
    object GestionReservas : Screen("gestion_reservas")
    object Ingresos : Screen("ingresos")
}