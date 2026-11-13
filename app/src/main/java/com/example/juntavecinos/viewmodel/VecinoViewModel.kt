package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.model.Vecino
import com.example.juntavecinos.repository.VecinoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VecinoViewModel(private val repository: VecinoRepository) : ViewModel() {

    val todosLosVecinos: Flow<List<Vecino>> = repository.todosLosVecinos

    private val TextoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = TextoBusqueda.asStateFlow()

    private val ErrorFormulario = MutableStateFlow<String?>(null)
    val errorFormulario: StateFlow<String?> = ErrorFormulario.asStateFlow()

    init {
        viewModelScope.launch {
            repository.asegurarPadronInicial()
        }
    }

    fun alCambiarBusqueda(valor: String) {
        TextoBusqueda.value = valor
    }

    fun limpiarErrorFormulario() {
        ErrorFormulario.value = null
    }

    fun agregarVecino(
        rut: String,
        nombre: String,
        alGuardarExitoso: () -> Unit
    ) {
        val rutLimpio = rut.trim()
        val nombreLimpio = nombre.trim()

        if (nombreLimpio.isBlank() || rutLimpio.isBlank()) {
            ErrorFormulario.value = "Completa el nombre y el RUT del vecino."
            return
        }

        viewModelScope.launch {
            if (repository.existeVecino(rutLimpio)) {
                ErrorFormulario.value = "Este RUT ya se encuentra registrado en el sistema."
            } else {
                repository.insertarVecino(
                    Vecino(rut = rutLimpio, nombre = nombreLimpio)
                )
                ErrorFormulario.value = null
                alGuardarExitoso()
            }
        }
    }

    fun eliminarVecino(vecino: Vecino) {
        viewModelScope.launch {
            repository.eliminarVecino(vecino)
        }
    }
}