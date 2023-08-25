package com.pe.edu.idat.grupo2.data.response

import com.pe.edu.idat.grupo2.model.Recetas

// Clase de datos que representa una respuesta de lista de recetas.
data class ListaRecetaResponse(
    val meals:List<Recetas>
)
