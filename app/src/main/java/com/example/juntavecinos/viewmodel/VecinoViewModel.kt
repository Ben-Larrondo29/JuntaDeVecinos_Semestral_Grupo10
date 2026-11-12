package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juntavecinos.model.Vecino
import com.example.juntavecinos.repository.VecinoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class VecinoViewModel(private val repository: VecinoRepository) : ViewModel() {

    val todosLosVecinos: Flow<List<Vecino>> = repository.todosLosVecinos

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    init {
        precargarVecinosIniciales()
    }

    private fun precargarVecinosIniciales() {
        viewModelScope.launch {
            val actuales = repository.todosLosVecinos.first()
            if (actuales.size <= 5) {
                val vecinosIniciales = listOf(
                    Vecino(rut = "12.345.678-9", nombre = "María González Soto"),
                    Vecino(rut = "14.876.543-2", nombre = "Juan Carlos Muñoz"),
                    Vecino(rut = "16.234.890-1", nombre = "Carmen Gloria Rojas"),
                    Vecino(rut = "18.456.123-K", nombre = "Pedro Pablo Silva"),
                    Vecino(rut = "15.987.321-4", nombre = "Rosa Elena Contreras"),
                    Vecino(rut = "11.234.567-8", nombre = "Luis Alberto Morales"),
                    Vecino(rut = "13.654.987-0", nombre = "Patricia Andrea Díaz"),
                    Vecino(rut = "17.890.123-5", nombre = "Jorge Eduardo Fuentes"),
                    Vecino(rut = "19.123.456-7", nombre = "Daniela Fernanda Vargas"),
                    Vecino(rut = "10.987.654-3", nombre = "Héctor Manuel Castro"),
                    Vecino(rut = "15.432.198-6", nombre = "Verónica Isabel Sepúlveda"),
                    Vecino(rut = "16.789.432-K", nombre = "Cristian Andrés Espinoza"),
                    Vecino(rut = "13.210.987-4", nombre = "Mónica Beatriz Araya"),
                    Vecino(rut = "18.901.234-1", nombre = "Sebastián Ignacio Tapia"),
                    Vecino(rut = "14.567.890-9", nombre = "Claudia Lorena Valenzuela"),
                    Vecino(rut = "12.876.543-7", nombre = "Ricardo Antonio Reyes"),
                    Vecino(rut = "17.345.678-2", nombre = "Camila Javiera Carrasco"),
                    Vecino(rut = "19.654.321-8", nombre = "Felipe Alejandro Gutiérrez"),
                    Vecino(rut = "11.876.234-5", nombre = "Teresa del Carmen Pizarro"),
                    Vecino(rut = "16.012.345-3", nombre = "Mauricio Esteban Salazar")
                )
                vecinosIniciales.forEach { vecino ->
                    repository.insertarVecino(vecino)
                }
            }
        }
    }

    fun alCambiarBusqueda(valor: String) {
        _textoBusqueda.value = valor
    }

    fun guardarVecino(vecino: Vecino) {
        viewModelScope.launch {
            repository.insertarVecino(vecino)
        }
    }

    fun eliminarVecino(vecino: Vecino) {
        viewModelScope.launch {
            repository.eliminarVecino(vecino)
        }
    }
}