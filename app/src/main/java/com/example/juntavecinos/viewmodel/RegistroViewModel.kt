package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.model.Reserva
import com.example.juntavecinos.repository.ReservaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReservaUiState(
    val nombreVecino: String = "",
    val rutVecino: String = "",
    val fecha: String = "",
    val horaInicio: String = "",
    val horaFin: String = "",
    val motivo: String = "",
    val aceptaReglamento: Boolean = false,
    val errorMensaje: String? = null
)

class ReservaViewModel(private val repository: ReservaRepository) : ViewModel() {

    private val _estadoFormulario = MutableStateFlow(ReservaUiState())
    val estadoFormulario: StateFlow<ReservaUiState> = _estadoFormulario.asStateFlow()

    val todasLasReservas = repository.todasLasReservas

    fun alCambiarNombre(valor: String) { _estadoFormulario.update { it.copy(nombreVecino = valor) } }
    fun alCambiarRut(valor: String) { _estadoFormulario.update { it.copy(rutVecino = valor) } }
    fun alCambiarFecha(valor: String) { _estadoFormulario.update { it.copy(fecha = valor) } }
    fun alCambiarHoraInicio(valor: String) { _estadoFormulario.update { it.copy(horaInicio = valor) } }
    fun alCambiarHoraFin(valor: String) { _estadoFormulario.update { it.copy(horaFin = valor) } }
    fun alCambiarMotivo(valor: String) { _estadoFormulario.update { it.copy(motivo = valor) } }
    fun alCambiarAceptaReglamento(valor: Boolean) { _estadoFormulario.update { it.copy(aceptaReglamento = valor) } }

    fun guardarReserva(onExito: () -> Unit) {
        val actual = _estadoFormulario.value
        if (actual.nombreVecino.isBlank() || actual.fecha.isBlank() || !actual.aceptaReglamento) {
            _estadoFormulario.update { it.copy(errorMensaje = "Complete los campos obligatorios") }
            return
        }

        val nuevaReserva = Reserva(
            nombreVecino = actual.nombreVecino,
            rutVecino = actual.rutVecino,
            fecha = actual.fecha,
            horaInicio = actual.horaInicio,
            horaFin = actual.horaFin,
            motivo = actual.motivo,
            estado = "PENDIENTE"
        )

        viewModelScope.launch {
            repository.insertarReserva(nuevaReserva)
            _estadoFormulario.value = ReservaUiState()
            onExito()
        }
    }

    fun cambiarEstadoReserva(reserva: Reserva, nuevoEstado: String) {
        viewModelScope.launch {
            repository.actualizarReserva(reserva.copy(estado = nuevoEstado))
        }
    }
}