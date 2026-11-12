package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.model.Administrador
import com.example.juntavecinos.repository.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class RolUsuario {
    NINGUNO,
    VECINO,
    ADMINISTRADOR
}

data class LoginUiState(
    val rolSeleccionado: RolUsuario = RolUsuario.VECINO,
    val rutVecino: String = "",
    val nombreVecino: String = "",
    val usuarioAdmin: String = "",
    val claveAdmin: String = "",
    val rolActivo: RolUsuario = RolUsuario.NINGUNO,
    val adminActivo: Administrador? = null,
    val errorMensaje: String? = null
)

class LoginViewModel(
    private val administradorRepository: AdminRepository
) : ViewModel() {

    private val EstadoLogin = MutableStateFlow(LoginUiState())
    val estadoLogin: StateFlow<LoginUiState> = EstadoLogin.asStateFlow()

    init {
        viewModelScope.launch {
            administradorRepository.asegurarAdminPorDefecto()
        }
    }

    fun alSeleccionarTipoLogin(rol: RolUsuario) {
        EstadoLogin.update {
            it.copy(rolSeleccionado = rol, errorMensaje = null)
        }
    }

    fun alCambiarRut(nuevoRut: String) {
        EstadoLogin.update { it.copy(rutVecino = nuevoRut, errorMensaje = null) }
    }

    fun alCambiarNombre(nuevoNombre: String) {
        EstadoLogin.update { it.copy(nombreVecino = nuevoNombre, errorMensaje = null) }
    }

    fun alCambiarUsuarioAdmin(nuevoUsuario: String) {
        EstadoLogin.update { it.copy(usuarioAdmin = nuevoUsuario, errorMensaje = null) }
    }

    fun alCambiarClaveAdmin(nuevaClave: String) {
        EstadoLogin.update { it.copy(claveAdmin = nuevaClave, errorMensaje = null) }
    }

    fun iniciarSesion(alExito: () -> Unit) {
        val actual = EstadoLogin.value

        if (actual.rolSeleccionado == RolUsuario.VECINO) {
            if (actual.rutVecino.isBlank() || actual.nombreVecino.isBlank()) {
                EstadoLogin.update {
                    it.copy(errorMensaje = "Ingresa tu nombre y RUT para continuar.")
                }
                return
            }
            EstadoLogin.update {
                it.copy(rolActivo = RolUsuario.VECINO, adminActivo = null, errorMensaje = null)
            }
            alExito()
        } else if (actual.rolSeleccionado == RolUsuario.ADMINISTRADOR) {
            if (actual.usuarioAdmin.isBlank() || actual.claveAdmin.isBlank()) {
                EstadoLogin.update {
                    it.copy(errorMensaje = "Ingresa usuario y contraseña.")
                }
                return
            }

            viewModelScope.launch {
                val adminEncontrado = administradorRepository.autenticar(
                    usuario = actual.usuarioAdmin.trim(),
                    clave = actual.claveAdmin.trim()
                )
                if (adminEncontrado != null) {
                    EstadoLogin.update {
                        it.copy(
                            rolActivo = RolUsuario.ADMINISTRADOR,
                            adminActivo = adminEncontrado,
                            errorMensaje = null
                        )
                    }
                    alExito()
                } else {
                    EstadoLogin.update {
                        it.copy(errorMensaje = "Credenciales de administrador inválidas.")
                    }
                }
            }
        }
    }

    fun cerrarSesion() {
        EstadoLogin.value = LoginUiState()
    }
}