package com.example.juntavecinos.repository

import com.example.juntavecinos.model.Vecino
import com.example.juntavecinos.model.VecinoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class VecinoRepository(private val vecinoDao: VecinoDao) {

    val todosLosVecinos: Flow<List<Vecino>> = vecinoDao.obtenerTodosLosVecinos()

    private var padronVerificado = false

    suspend fun insertarVecino(vecino: Vecino) {
        vecinoDao.insertarVecino(
            vecino.copy(
                rut = vecino.rut.trim(),
                nombre = vecino.nombre.trim()
            )
        )
    }

    suspend fun eliminarVecino(vecino: Vecino) {
        vecinoDao.eliminarVecino(vecino)
    }

    suspend fun existeVecino(rut: String): Boolean {
        val rutNormalizado = normalizarRut(rut)
        val listaVecinos = vecinoDao.obtenerTodosLosVecinos().first()
        return listaVecinos.any { normalizarRut(it.rut) == rutNormalizado }
    }

    suspend fun buscarVecinoEnSistema(rutIngresado: String): Vecino? {
        asegurarPadronInicial()
        val rutNormalizado = normalizarRut(rutIngresado)
        val listaVecinos = vecinoDao.obtenerTodosLosVecinos().first()
        return listaVecinos.find { normalizarRut(it.rut) == rutNormalizado }
    }

    private fun normalizarRut(rut: String): String {
        return rut.replace(".", "").replace(" ", "").trim().uppercase()
    }

    suspend fun asegurarPadronInicial() {
        if (padronVerificado) return
        padronVerificado = true

        val actuales = vecinoDao.obtenerTodosLosVecinos().first()
        if (actuales.isEmpty()) {
            val vecinosIniciales = listOf(
                Vecino(rut = "21.511.541-0", nombre = "Kevin Alexandre Araya Plaza"),
                Vecino(rut = "12.345.678-9", nombre = "María González Soto"),
                Vecino(rut = "14.876.543-2", nombre = "Juan Carlos Muñoz"),
                Vecino(rut = "16.234.890-1", nombre = "Carmen Gloria Rojas"),
                Vecino(rut = "18.456.123-K", nombre = "Pedro Pablo Silva"),
                Vecino(rut = "15.987.321-4", nombre = "Rosa Elena Contreras"),
                Vecino(rut = "11.234.567-8", nombre = "Luis Alberto Morales"),
                Vecino(rut = "13.654.987-0", nombre = "Patricia Andrea Díaz"),
                Vecino(rut = "17.890.123-5", nombre = "Jorge Eduardo Fuentes"),
                Vecino(rut = "19.123.456-7", nombre = "Daniela Fernanda Vargas"),
                Vecino(rut = "10.987.654-3", nombre = "Héctor Manuel Castro"),
                Vecino(rut = "15.432.198-6", nombre = "Verónica Isabel Sepúlveda"),
                Vecino(rut = "16.789.432-K", nombre = "Cristian Andrés Espinoza"),
                Vecino(rut = "13.210.987-4", nombre = "Mónica Beatriz Araya"),
                Vecino(rut = "18.901.234-1", nombre = "Sebastián Ignacio Tapia"),
                Vecino(rut = "14.567.890-9", nombre = "Claudia Lorena Valenzuela"),
                Vecino(rut = "12.876.543-7", nombre = "Ricardo Antonio Reyes"),
                Vecino(rut = "17.345.678-2", nombre = "Camila Javiera Carrasco"),
                Vecino(rut = "19.654.321-8", nombre = "Felipe Alejandro Gutiérrez"),
                Vecino(rut = "11.876.234-5", nombre = "Teresa del Carmen Pizarro"),
                Vecino(rut = "16.012.345-3", nombre = "Mauricio Esteban Salazar")
            )
            vecinosIniciales.forEach { vecinoDao.insertarVecino(it) }
        }
    }
}