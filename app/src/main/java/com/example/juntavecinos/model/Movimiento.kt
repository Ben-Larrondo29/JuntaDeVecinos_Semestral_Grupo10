package com.example.juntavecinos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimientos_financieros")
data class MovimientoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: String,
    val concepto: String,
    val monto: Double,
    val fecha: String,
    val comprobanteUri: String? = null
)