package com.example.juntavecinos.repository

import com.example.juntavecinos.model.Aviso
import com.example.juntavecinos.model.AvisoDao
import kotlinx.coroutines.flow.Flow

class AvisoRepository(private val avisoDao: AvisoDao) {

    val todosLosAvisos: Flow<List<Aviso>> = avisoDao.obtenerTodosLosAvisos()

    suspend fun insertarAviso(aviso: Aviso) {
        avisoDao.insertarAviso(aviso)
    }

    suspend fun eliminarAviso(aviso: Aviso) {
        avisoDao.eliminarAviso(aviso)
    }
}