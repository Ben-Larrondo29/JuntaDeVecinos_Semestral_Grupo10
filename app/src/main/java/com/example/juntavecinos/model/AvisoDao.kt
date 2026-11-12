package com.example.juntavecinos.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AvisoDao {

    @Query("SELECT * FROM avisos ORDER BY rowid DESC")
    fun obtenerTodosLosAvisos(): Flow<List<Aviso>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAviso(aviso: Aviso)

    @Delete
    suspend fun eliminarAviso(aviso: Aviso)
}