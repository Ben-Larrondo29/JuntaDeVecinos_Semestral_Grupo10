package com.example.juntavecinos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "administradores")
data class Administrador(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuario: String,
    val clave: String,
    val nombreCompleto: String,
    val cargo: String // Presidente/a, Secretario/a, Tesorero/a
)