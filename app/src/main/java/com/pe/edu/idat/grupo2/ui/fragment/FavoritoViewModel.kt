package com.pe.edu.idat.grupo2.ui.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pe.edu.idat.grupo2.data.db.RecetaDatabase
import com.pe.edu.idat.grupo2.data.repository.RecetaRepository
import com.pe.edu.idat.grupo2.model.Recetas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecetaRepository // Repositorio para interactuar con los datos.
    private val _favorites: MutableLiveData<List<Recetas>> = MutableLiveData() // LiveData mutable para almacenar la lista de recetas favoritas.
    val favorites: LiveData<List<Recetas>> = _favorites // LiveData inmutable para exponer la lista de recetas favoritas.

    init {
        // Obtiene una instancia del DAO de la base de datos y crea el repositorio.
        val dao = RecetaDatabase.getDatabase(application).recetaDao()
        repository = RecetaRepository(dao)
    }

    // Función para obtener las recetas favoritas en segundo plano.
    fun getFavorities() {
        viewModelScope.launch(Dispatchers.IO) {
            // Llama a la función en el repositorio para obtener las recetas favoritas.
            val data = repository.getFavorites()
            data?.let {
                // Publica las recetas favoritas en el LiveData para que la interfaz de usuario pueda observar los cambios.
                _favorites.postValue(it)
            }
        }
    }
}