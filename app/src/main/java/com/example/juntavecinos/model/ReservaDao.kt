package com.example.juntavecinos.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {
    @Query("SELECT * FROM reservas ORDER BY fecha DESC")
    fun obtenerTodasLasReservas(): Flow<List<Reserva>> // <-- Debe decir Reserva

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReserva(reserva: Reserva)

    @Update
    suspend fun actualizarReserva(reserva: Reserva)

    @Delete
    suspend fun eliminarReserva(reserva: Reserva)
}