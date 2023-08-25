package com.pe.edu.idat.grupo2.data.repository

import com.pe.edu.idat.grupo2.data.DatosResults
import com.pe.edu.idat.grupo2.data.db.RecetaDao
import com.pe.edu.idat.grupo2.data.response.ListaRecetaResponse
import com.pe.edu.idat.grupo2.data.retrofit.RetrofitHelper
import com.pe.edu.idat.grupo2.model.Recetas

// Implementación del DAO agregando una propiedad 'recetaDao' al constructor.
class RecetaRepository(val recetaDao: RecetaDao? = null) {

    // Función suspendida para obtener recetas.
    suspend fun getRecetas(): DatosResults<ListaRecetaResponse> {
        return try {
            // Llama a una función en 'RetrofitHelper' para obtener todas las recetas.
            val response = RetrofitHelper.recetaInstance.getAllRecetas()
            // Devuelve un resultado exitoso con la respuesta obtenida.
            DatosResults.Success(response)
        } catch (e: Exception) {
            // En caso de error, devuelve un resultado de error con la excepción capturada.
            DatosResults.Error(e)
        }
    }

    // Función suspendida para obtener favoritos.
    // 1. Trae favoritos llamando al método 'getFavorites' en el DAO.
    suspend fun getFavorites(): List<Recetas>? {
        return recetaDao?.getFavorites()
    }

    // Función suspendida para agregar una receta a la lista de favoritos.
    // 2. Agrega favoritos llamando al método 'addToFavorite' en el DAO.
    suspend fun addToFavorites(recetas: Recetas) {
        recetaDao?.addToFavorite(recetas)
    }

    // Función suspendida para eliminar una receta de la lista de favoritos.
    // 3. Elimina de favoritos llamando al método 'deleteFavorite' en el DAO.
    suspend fun deleteToFavorites(recetas: Recetas) {
        recetaDao?.deleteFavorite(recetas)
    }
}