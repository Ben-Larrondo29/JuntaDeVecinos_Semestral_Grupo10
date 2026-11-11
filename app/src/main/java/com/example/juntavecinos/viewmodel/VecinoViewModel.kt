package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.model.Vecino
import com.example.juntavecinos.repository.VecinoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VecinoUiState(
    val nombre: String = "",
    val rut: String = "",
    val direccion: String = "",
    val correo: String = ""
)

class VecinoViewModel(private val repository: VecinoRepository) : ViewModel() {

    private val _estadoFormulario = MutableStateFlow(VecinoUiState())
    val estadoFormulario: StateFlow<VecinoUiState> = _estadoFormulario.asStateFlow()

    val todosLosVecinos = repository.todosLosVecinos

    fun alCambiarNombre(valor: String) { _estadoFormulario.update { it.copy(nombre = valor) } }
    fun alCambiarRut(valor: String) { _estadoFormulario.update { it.copy(rut = valor) } }
    fun alCambiarDireccion(valor: String) { _estadoFormulario.update { it.copy(direccion = valor) } }
    fun alCambiarCorreo(valor: String) { _estadoFormulario.update { it.copy(correo = valor) } }

    fun guardarVecino(onExito: () -> Unit) {
        val actual = _estadoFormulario.value
        if (actual.nombre.isBlank() || actual.rut.isBlank()) return

        val nuevoVecino = Vecino(
            nombre = actual.nombre,
            rut = actual.rut,
            direccion = actual.direccion,
            correo = actual.correo
        )

        viewModelScope.launch {
            repository.insertarVecino(nuevoVecino)
            _estadoFormulario.value = VecinoUiState()
            onExito()
        }
    }
}