package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.model.Aviso
import com.example.juntavecinos.repository.AvisoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AvisoUiState(
    val titulo: String = "",
    val categoria: String = "Comunidad",
    val fecha: String = "",
    val descripcion: String = "",
    val importante: Boolean = false,
    val errorMensaje: String? = null
)

class AvisoViewModel(private val repository: AvisoRepository) : ViewModel() {

    private val EstadoFormulario = MutableStateFlow(AvisoUiState())
    val estadoFormulario: StateFlow<AvisoUiState> = EstadoFormulario.asStateFlow()

    val todosLosAvisos: Flow<List<Aviso>> = repository.todosLosAvisos

    fun alCambiarTitulo(valor: String) {
        EstadoFormulario.update { it.copy(titulo = valor, errorMensaje = null) }
    }

    fun alCambiarCategoria(valor: String) {
        EstadoFormulario.update { it.copy(categoria = valor, errorMensaje = null) }
    }

    fun alCambiarFecha(valor: String) {
        EstadoFormulario.update { it.copy(fecha = valor, errorMensaje = null) }
    }

    fun alCambiarDescripcion(valor: String) {
        EstadoFormulario.update { it.copy(descripcion = valor, errorMensaje = null) }
    }

    fun alCambiarImportante(valor: Boolean) {
        EstadoFormulario.update { it.copy(importante = valor) }
    }

    fun guardarAviso(onExito: () -> Unit) {
        val actual = EstadoFormulario.value

        if (actual.titulo.isBlank() || actual.fecha.isBlank() || actual.descripcion.isBlank()) {
            EstadoFormulario.update {
                it.copy(errorMensaje = "Completa los campos obligatorios (*)")
            }
            return
        }

        val nuevoAviso = Aviso(
            titulo = actual.titulo.trim(),
            categoria = actual.categoria,
            fecha = actual.fecha.trim(),
            descripcion = actual.descripcion.trim(),
            importante = actual.importante
        )

        viewModelScope.launch {
            repository.insertarAviso(nuevoAviso)
            EstadoFormulario.value = AvisoUiState()
            onExito()
        }
    }

    fun eliminarAviso(aviso: Aviso) {
        viewModelScope.launch {
            repository.eliminarAviso(aviso)
        }
    }
}

class AvisoViewModelFactory(private val repository: AvisoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvisoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AvisoViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}