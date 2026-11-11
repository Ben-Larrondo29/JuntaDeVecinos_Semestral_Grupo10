package com.example.juntavecinos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservas")
data class ReservaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreVecino: String,
    val rutVecino: String,
    val fecha: String,           // Ejemplo: "2026-11-20"
    val horaInicio: String,      // Ejemplo: "15:00"
    val horaFin: String,         // Ejemplo: "20:00"
    val motivo: String,
    val estado: String = "PENDIENTE" // PENDIENTE, APROBADO, RECHAZADO
)