package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.model.Movimiento
import com.example.juntavecinos.repository.MovimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovimientoUiState(
    val tipo: String = "INGRESO",
    val concepto: String = "",
    val monto: String = "",
    val fecha: String = "",
    val comprobanteUri: String? = null
)

class MovimientoViewModel(private val repository: MovimientoRepository) : ViewModel() {

    private val _estadoFormulario = MutableStateFlow(MovimientoUiState())
    val estadoFormulario: StateFlow<MovimientoUiState> = _estadoFormulario.asStateFlow()

    val todosLosMovimientos = repository.todosLosMovimientos
    val totalIngresos = repository.totalIngresos
    val totalGastos = repository.totalGastos

    fun alCambiarTipo(valor: String) { _estadoFormulario.update { it.copy(tipo = valor) } }
    fun alCambiarConcepto(valor: String) { _estadoFormulario.update { it.copy(concepto = valor) } }
    fun alCambiarMonto(valor: String) { _estadoFormulario.update { it.copy(monto = valor) } }
    fun alCambiarFecha(valor: String) { _estadoFormulario.update { it.copy(fecha = valor) } }
    fun alCambiarComprobanteUri(uri: String?) { _estadoFormulario.update { it.copy(comprobanteUri = uri) } }

    fun guardarMovimiento(onExito: () -> Unit) {
        val actual = _estadoFormulario.value
        val montoDouble = actual.monto.toDoubleOrNull() ?: return

        val nuevoMovimiento = Movimiento(
            tipo = actual.tipo,
            concepto = actual.concepto,
            monto = montoDouble,
            fecha = actual.fecha,
            comprobanteUri = actual.comprobanteUri
        )

        viewModelScope.launch {
            repository.insertarMovimiento(nuevoMovimiento)
            _estadoFormulario.value = MovimientoUiState()
            onExito()
        }
    }
}