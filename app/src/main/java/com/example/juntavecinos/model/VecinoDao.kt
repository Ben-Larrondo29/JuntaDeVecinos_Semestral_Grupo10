package com.example.juntavecinos.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VecinoDao {

    @Query("SELECT * FROM vecinos ORDER BY nombre ASC")
    fun obtenerTodosLosVecinos(): Flow<List<Vecino>>

    @Query("SELECT * FROM vecinos WHERE rut = :rut LIMIT 1")
    suspend fun obtenerVecinoPorRut(rut: String): Vecino?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVecino(vecino: Vecino)

    @Update
    suspend fun actualizarVecino(vecino: Vecino)

    @Delete
    suspend fun eliminarVecino(vecino: Vecino)
}