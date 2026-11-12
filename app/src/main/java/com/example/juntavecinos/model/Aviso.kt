package com.example.juntavecinos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avisos")
data class Aviso(
    @PrimaryKey
    val titulo: String,
    val categoria: String,
    val fecha: String,
    val descripcion: String,
    val importante: Boolean = false
)