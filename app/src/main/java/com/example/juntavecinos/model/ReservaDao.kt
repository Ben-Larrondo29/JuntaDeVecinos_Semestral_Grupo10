package com.example.juntavecinos.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {
    @Query("SELECT * FROM reservas ORDER BY id DESC")
    fun obtenerTodasLasReservas(): Flow<List<ReservaEntity>>

    @Query("SELECT * FROM reservas WHERE estado = :estado")
    fun obtenerReservasPorEstado(estado: String): Flow<List<ReservaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReserva(reserva: ReservaEntity)

    @Update
    suspend fun actualizarReserva(reserva: ReservaEntity)

    @Delete
    suspend fun eliminarReserva(reserva: ReservaEntity)
}