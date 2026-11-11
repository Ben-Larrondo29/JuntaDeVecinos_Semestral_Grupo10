package com.example.juntavecinos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vecinos")
data class Vecino(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String = "",
    val rut: String = "",
    val direccion: String = "",
    val correo: String = ""
)