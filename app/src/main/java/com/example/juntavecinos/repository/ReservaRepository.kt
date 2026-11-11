package com.example.juntavecinos.repository

import com.example.juntavecinos.model.ReservaDao
import com.example.juntavecinos.model.ReservaEntity
import kotlinx.coroutines.flow.Flow

class ReservaRepository(private val reservaDao: ReservaDao) {

    val todasLasReservas: Flow<List<ReservaEntity>> = reservaDao.obtenerTodasLasReservas()

    fun obtenerReservasPorEstado(estado: String): Flow<List<ReservaEntity>> {
        return reservaDao.obtenerReservasPorEstado(estado)
    }

    suspend fun insertarReserva(reserva: ReservaEntity) {
        reservaDao.insertarReserva(reserva)
    }

    suspend fun actualizarReserva(reserva: ReservaEntity) {
        reservaDao.actualizarReserva(reserva)
    }

    suspend fun eliminarReserva(reserva: ReservaEntity) {
        reservaDao.eliminarReserva(reserva)
    }
}