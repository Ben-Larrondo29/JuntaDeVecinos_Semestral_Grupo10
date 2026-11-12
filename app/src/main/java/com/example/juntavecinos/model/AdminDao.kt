package com.example.juntavecinos.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AdminDao {
    @Query("SELECT * FROM administradores WHERE usuario = :usuario AND clave = :clave LIMIT 1")
    suspend fun autenticar(usuario: String, clave: String): Administrador?

    @Query("SELECT * FROM administradores ORDER BY nombreCompleto ASC")
    fun obtenerTodosLosAdministradores(): Flow<List<Administrador>>

    @Query("SELECT COUNT(*) FROM administradores")
    suspend fun contarAdministradores(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAdministrador(administrador: Administrador)

    @Delete
    suspend fun eliminarAdministrador(administrador: Administrador)
}