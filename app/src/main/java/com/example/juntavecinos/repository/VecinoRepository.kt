package com.example.juntavecinos.repository

import com.example.juntavecinos.model.Vecino
import com.example.juntavecinos.model.VecinoDao
import kotlinx.coroutines.flow.Flow

class VecinoRepository(private val vecinoDao: VecinoDao) {

    // Flujo en tiempo real con todos los vecinos registrados
    val todosLosVecinos: Flow<List<Vecino>> = vecinoDao.obtenerTodosLosVecinos()

    // Insertar un nuevo vecino
    suspend fun insertarVecino(vecino: Vecino) {
        vecinoDao.insertarVecino(vecino)
    }

    // Buscar un vecino por su RUT
    suspend fun obtenerVecinoPorRut(rut: String): Vecino? {
        return vecinoDao.obtenerVecinoPorRut(rut)
    }

    // Actualizar datos del vecino
    suspend fun actualizarVecino(vecino: Vecino) {
        vecinoDao.actualizarVecino(vecino)
    }

    // Eliminar un vecino
    suspend fun eliminarVecino(vecino: Vecino) {
        vecinoDao.eliminarVecino(vecino)
    }
}