package com.pe.edu.idat.grupo2.ui.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pe.edu.idat.grupo2.data.db.RecetaDao
import com.pe.edu.idat.grupo2.data.db.RecetaDatabase
import com.pe.edu.idat.grupo2.data.repository.RecetaRepository
import com.pe.edu.idat.grupo2.model.Recetas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecetaDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecetaRepository // Repositorio para interactuar con los datos.

    // Inicialización del ViewModel.
    init {
        // Obtiene una instancia del DAO de la base de datos y crea el repositorio.
        val dao = RecetaDatabase.getDatabase(application).recetaDao()
        repository = RecetaRepository(dao)
    }

    // Función para agregar una receta a la lista de favoritos.
    fun addToFavorites(recetas: Recetas) {
        viewModelScope.launch(Dispatchers.IO) {
            // Llama a la función en el repositorio para agregar la receta a favoritos en un hilo de fondo.
            repository.addToFavorites(recetas)
        }
    }

    // Función para eliminar una receta de la lista de favoritos.
    fun deleteRecetas(recetas: Recetas) {
        viewModelScope.launch {
            // Llama a la función en el repositorio para eliminar la receta de favoritos en un hilo de fondo.
            repository.deleteToFavorites(recetas)
        }
    }
}