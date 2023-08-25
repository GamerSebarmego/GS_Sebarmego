package com.pe.edu.idat.grupo2.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pe.edu.idat.grupo2.data.DatosResults
import com.pe.edu.idat.grupo2.data.repository.RecetaRepository
import com.pe.edu.idat.grupo2.model.Recetas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListApiViewModel : ViewModel() {
    private val _recetas: MutableLiveData<List<Recetas>> = MutableLiveData<List<Recetas>>()
    val recetas: LiveData<List<Recetas>> = _recetas
    val repository = RecetaRepository()

    // CoRutinas: Esta función se ejecuta en un hilo de fondo utilizando corutinas.
    fun getRecetaFromService() {
        viewModelScope.launch(Dispatchers.IO) {
            // Llama a la función en el repositorio para obtener recetas desde un servicio.
            val response = repository.getRecetas()

            // Maneja la respuesta del servicio.
            when (response) {
                // Si la respuesta es exitosa:
                is DatosResults.Success -> {
                    // Publica los datos de las recetas en el LiveData para que la interfaz de usuario pueda observar los cambios.
                    _recetas.postValue(response.data.meals)
                }
                // Si la respuesta tiene un error:
                is DatosResults.Error -> {
                    // Aquí se podría manejar el error, pero en este código, no se está haciendo nada específico en caso de error.
                }
            }
        }
    }
}