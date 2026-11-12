package com.example.juntavecinos.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {
    @Query("SELECT * FROM movimientos_financieros ORDER BY id DESC")
    fun obtenerTodosLosMovimientos(): Flow<List<Movimiento>>

    @Query("SELECT SUM(monto) FROM movimientos_financieros WHERE tipo = 'INGRESO'")
    fun obtenerTotalIngresos(): Flow<Double?>

    @Query("SELECT SUM(monto) FROM movimientos_financieros WHERE tipo = 'GASTO'")
    fun obtenerTotalGastos(): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMovimiento(movimiento: Movimiento)
}