package com.example.juntavecinos.repository

import com.example.juntavecinos.model.Reserva
import com.example.juntavecinos.model.ReservaDao
import kotlinx.coroutines.flow.Flow

class ReservaRepository(private val reservaDao: ReservaDao) {

    val todasLasReservas: Flow<List<Reserva>> = reservaDao.obtenerTodasLasReservas()

    suspend fun insertarReserva(reserva: Reserva) {
        reservaDao.insertarReserva(reserva)
    }

    suspend fun actualizarReserva(reserva: Reserva) {
        reservaDao.actualizarReserva(reserva)
    }

    suspend fun eliminarReserva(reserva: Reserva) {
        reservaDao.eliminarReserva(reserva)
    }
}