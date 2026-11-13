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
    private val adminRepository: AdminRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(vecinoRepository, adminRepository) as T
            }
            modelClass.isAssignableFrom(ReservaViewModel::class.java) -> {
                ReservaViewModel(reservaRepository) as T
            }
            modelClass.isAssignableFrom(MovimientoViewModel::class.java) -> {
                MovimientoViewModel(movimientoRepository) as T
            }
            modelClass.isAssignableFrom(VecinoViewModel::class.java) -> {
                VecinoViewModel(vecinoRepository) as T
            }
            else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
    }
}