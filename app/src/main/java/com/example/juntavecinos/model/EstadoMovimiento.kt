package com.example.juntavecinos.model

data class EstadoMovimiento(
    val tipo: String = "INGRESO",
    val concepto: String = "",
    val monto: String = "",
    val fecha: String = "",
    val comprobanteUri: String? = null,
    val errores: MovimientoErrores = MovimientoErrores()
)

data class MovimientoErrores(
    val concepto: String? = null,
    val monto: String? = null,
    val fecha: String? = null
)