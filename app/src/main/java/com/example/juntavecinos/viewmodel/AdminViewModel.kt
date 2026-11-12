package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.model.Administrador
import com.example.juntavecinos.repository.AdminRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AdministradorUiState(
    val usuario: String = "",
    val clave: String = "",
    val nombreCompleto: String = "",
    val cargo: String = "",
    val errorMensaje: String? = null
)

class AdminViewModel(private val repository: AdminRepository) : ViewModel() {

    private val EstadoFormulario = MutableStateFlow(AdministradorUiState())
    val estadoFormulario: StateFlow<AdministradorUiState> = EstadoFormulario.asStateFlow()

    val todosLosAdministradores: Flow<List<Administrador>> = repository.todosLosAdministradores

    fun alCambiarUsuario(valor: String) {
        EstadoFormulario.update { it.copy(usuario = valor, errorMensaje = null) }
    }

    fun alCambiarClave(valor: String) {
        EstadoFormulario.update { it.copy(clave = valor, errorMensaje = null) }
    }

    fun alCambiarNombreCompleto(valor: String) {
        EstadoFormulario.update { it.copy(nombreCompleto = valor, errorMensaje = null) }
    }

    fun alCambiarCargo(valor: String) {
        EstadoFormulario.update { it.copy(cargo = valor, errorMensaje = null) }
    }

    fun guardarAdministrador() {
        val actual = EstadoFormulario.value
        if (actual.usuario.isBlank() || actual.clave.isBlank() ||
            actual.nombreCompleto.isBlank() || actual.cargo.isBlank()
        ) {
            EstadoFormulario.update {
                it.copy(errorMensaje = "Todos los campos son obligatorios.")
            }
            return
        }

        viewModelScope.launch {
            val nuevoAdmin = Administrador(
                usuario = actual.usuario.trim(),
                clave = actual.clave.trim(),
                nombreCompleto = actual.nombreCompleto.trim(),
                cargo = actual.cargo.trim()
            )
            repository.insertarAdministrador(nuevoAdmin)
            EstadoFormulario.value = AdministradorUiState()
        }
    }

    fun eliminarAdministrador(administrador: Administrador) {
        viewModelScope.launch {
            repository.eliminarAdministrador(administrador)
        }
    }
}