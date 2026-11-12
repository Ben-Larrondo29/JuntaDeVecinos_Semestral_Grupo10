package com.example.juntavecinos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.juntavecinos.repository.AdminRepository
import com.example.juntavecinos.repository.MovimientoRepository
import com.example.juntavecinos.repository.ReservaRepository
import com.example.juntavecinos.repository.VecinoRepository

class ViewModelFactory(
    private val reservaRepository: ReservaRepository,
    private val movimientoRepository: MovimientoRepository,
    private val vecinoRepository: VecinoRepository,
    private val administradorRepository: AdminRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ReservaViewModel::class.java) ->
                ReservaViewModel(reservaRepository) as T
            modelClass.isAssignableFrom(MovimientoViewModel::class.java) ->
                MovimientoViewModel(movimientoRepository) as T
            modelClass.isAssignableFrom(VecinoViewModel::class.java) ->
                VecinoViewModel(vecinoRepository) as T
            modelClass.isAssignableFrom(AdminViewModel::class.java) ->
                AdminViewModel(administradorRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(administradorRepository) as T
            else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
    }
}