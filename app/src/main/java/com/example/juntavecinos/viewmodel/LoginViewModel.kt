package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.repository.AdminRepository
import com.example.juntavecinos.repository.VecinoRepository
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
    val rolActivo: RolUsuario = RolUsuario.NINGUNO,
    val nombreVecino: String = "",
    val rutVecino: String = "",
    val errorMensaje: String? = null
)

@Suppress("UNUSED_PARAMETER")
class LoginViewModel(
    private val vecinoRepository: VecinoRepository,
    private val adminRepository: AdminRepository
) : ViewModel() {

    private val _estadoLogin = MutableStateFlow(LoginUiState())
    val estadoLogin: StateFlow<LoginUiState> = _estadoLogin.asStateFlow()

    fun limpiarError() {
        _estadoLogin.update { it.copy(errorMensaje = null) }
    }

    fun iniciarSesionVecino(
        rut: String,
        nombre: String = "",
        alExito: () -> Unit
    ) {
        if (rut.isBlank()) {
            _estadoLogin.update {
                it.copy(errorMensaje = "Ingresa tu RUT para continuar.")
            }
            return
        }

        viewModelScope.launch {
            val vecinoRegistrado = vecinoRepository.buscarVecinoEnSistema(rut)

            if (vecinoRegistrado == null) {
                _estadoLogin.update {
                    it.copy(
                        rolActivo = RolUsuario.NINGUNO,
                        errorMensaje = "Acceso denegado: este RUT no se encuentra registrado en la Población Zaror."
                    )
                }
            } else {
                if (nombre.isNotBlank() &&
                    !vecinoRegistrado.nombre.contains(nombre.trim(), ignoreCase = true)
                ) {
                    _estadoLogin.update {
                        it.copy(
                            rolActivo = RolUsuario.NINGUNO,
                            errorMensaje = "El nombre ingresado no coincide con el RUT registrado."
                        )
                    }
                    return@launch
                }

                _estadoLogin.update {
                    it.copy(
                        rolActivo = RolUsuario.VECINO,
                        nombreVecino = vecinoRegistrado.nombre,
                        rutVecino = vecinoRegistrado.rut,
                        errorMensaje = null
                    )
                }
                alExito()
            }
        }
    }

    fun iniciarSesionAdmin(
        usuario: String,
        clave: String,
        alExito: () -> Unit
    ) {
        if (usuario.isBlank() || clave.isBlank()) {
            _estadoLogin.update {
                it.copy(errorMensaje = "Completa usuario y contraseña de administrador.")
            }
            return
        }

        viewModelScope.launch {
            val esValido = usuario.trim() == "admin" && clave.trim() == "1234"

            if (esValido) {
                _estadoLogin.update {
                    it.copy(
                        rolActivo = RolUsuario.ADMINISTRADOR,
                        nombreVecino = "Directiva",
                        rutVecino = "",
                        errorMensaje = null
                    )
                }
                alExito()
            } else {
                _estadoLogin.update {
                    it.copy(errorMensaje = "Credenciales de administrador incorrectas.")
                }
            }
        }
    }

    fun cerrarSesion() {
        _estadoLogin.value = LoginUiState()
    }
}