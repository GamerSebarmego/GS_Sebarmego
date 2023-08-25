package com.pe.edu.idat.grupo2.data

// Clase sellada (sealed class) llamada DatosResults que representa un resultado de datos genéricos.
sealed class DatosResults<T>(data: T? = null, error: Exception? = null) {
    // Clase interna (inner data class) Success que representa un resultado exitoso.
    data class Success<T>(val data: T) : DatosResults<T>(data, null)

    // Clase interna (inner data class) Error que representa un resultado con un error.
    data class Error<T>(val error: Exception) : DatosResults<T>(null, error)
}