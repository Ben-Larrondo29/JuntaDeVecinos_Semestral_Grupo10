qpackage com.example.juntavecinos.model

// Estado del formulario de reserva
data class EstadoReserva(
    val nombreVecino: String = "",
    val rutVecino: String = "",
    val fecha: String = "",
    val horaInicio: String = "",
    val horaFin: String = "",
    val motivo: String = "",
    val aceptaReglamento: Boolean = false,
    val errores: ReservaErrores = ReservaErrores()
)

// Errores específicos por campo de texto
data class ReservaErrores(
    val nombreVecino: String? = null,
    val rutVecino: String? = null,
    val fecha: String? = null,
    val horaInicio: String? = null,
    val horaFin: String? = null,
    val motivo: String? = null
)