package com.example.juntavecinos.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = MoradoPrincipal,
    onPrimary = Color.White,
    primaryContainer = MoradoFondoSuave,
    onPrimaryContainer = MoradoOscuro,
    secondary = MoradoSecundario,
    onSecondary = Color.White,
    secondaryContainer = MoradoFondoSuave,
    onSecondaryContainer = MoradoOscuro,
    background = MoradoSuperficie,
    surface = Color.White,
    surfaceContainerLow = MoradoFondoSuave // Color base para barras laterales (NavigationDrawer)
)

private val DarkColorScheme = darkColorScheme(
    primary = MoradoClaroDark,
    onPrimary = MoradoOscuro,
    primaryContainer = MoradoContenedorDark,
    onPrimaryContainer = MoradoFondoSuave,
    secondary = MoradoClaroDark,
    onSecondary = MoradoOscuro
)

@Composable
fun JuntaVecinosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Desactivado para mantener siempre el morado
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}