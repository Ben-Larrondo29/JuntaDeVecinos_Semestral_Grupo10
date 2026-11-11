package com.example.juntavecinos.repository

import com.example.juntavecinos.model.MovimientoDao
import com.example.juntavecinos.model.MovimientoEntity
import kotlinx.coroutines.flow.Flow

class MovimientoRepository(private val movimientoDao: MovimientoDao) {

    val todosLosMovimientos: Flow<List<MovimientoEntity>> = movimientoDao.obtenerTodosLosMovimientos()
    val totalIngresos: Flow<Double?> = movimientoDao.obtenerTotalIngresos()
    val totalGastos: Flow<Double?> = movimientoDao.obtenerTotalGastos()

    suspend fun insertarMovimiento(movimiento: MovimientoEntity) {
        movimientoDao.insertarMovimiento(movimiento)
    }
}