package com.example.juntavecinos.repository

import com.example.juntavecinos.model.Administrador
import com.example.juntavecinos.model.AdminDao
import kotlinx.coroutines.flow.Flow

class AdminRepository(private val administradorDao: AdminDao) {

    val todosLosAdministradores: Flow<List<Administrador>> =
        administradorDao.obtenerTodosLosAdministradores()

    suspend fun autenticar(usuario: String, clave: String): Administrador? {
        return administradorDao.autenticar(usuario, clave)
    }

    suspend fun insertarAdministrador(administrador: Administrador) {
        administradorDao.insertarAdministrador(administrador)
    }

    suspend fun eliminarAdministrador(administrador: Administrador) {
        administradorDao.eliminarAdministrador(administrador)
    }

    suspend fun asegurarAdminPorDefecto() {
        if (administradorDao.contarAdministradores() == 0) {
            administradorDao.insertarAdministrador(
                Administrador(
                    usuario = "admin",
                    clave = "1234",
                    nombreCompleto = "Directiva Población Zaror",
                    cargo = "Administrador General"
                )
            )
        }
    }
}