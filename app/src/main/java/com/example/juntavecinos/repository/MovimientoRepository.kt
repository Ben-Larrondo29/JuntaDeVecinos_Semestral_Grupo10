package com.example.juntavecinos.repository

import com.example.juntavecinos.model.Movimiento
import com.example.juntavecinos.model.MovimientoDao
import kotlinx.coroutines.flow.Flow

class MovimientoRepository(private val movimientoDao: MovimientoDao) {

    val todosLosMovimientos: Flow<List<Movimiento>> = movimientoDao.obtenerTodosLosMovimientos()
    val totalIngresos: Flow<Double?> = movimientoDao.obtenerTotalIngresos()
    val totalGastos: Flow<Double?> = movimientoDao.obtenerTotalGastos()

    suspend fun insertarMovimiento(movimiento: Movimiento) {
        movimientoDao.insertarMovimiento(movimiento)
    }
}