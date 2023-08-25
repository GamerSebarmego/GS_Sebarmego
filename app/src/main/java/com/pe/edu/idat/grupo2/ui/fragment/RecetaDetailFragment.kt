package com.pe.edu.idat.grupo2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.FragmentRecetaDetailBinding
import com.pe.edu.idat.grupo2.model.Recetas


class RecetaDetailFragment : Fragment() {
    private lateinit var binding: FragmentRecetaDetailBinding
    private val args: RecetaDetailFragmentArgs by navArgs() // Argumentos pasados a través de la navegación.
    private lateinit var receta: Recetas // Objeto Recetas para mostrar detalles.

    private lateinit var viewModel: RecetaDetailViewModel // ViewModel para esta pantalla.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtiene el objeto Recetas pasado como argumento desde la navegación.
        receta = args.receta

        // Inicializa el ViewModel asociado a esta actividad.
        viewModel = ViewModelProvider(requireActivity())[RecetaDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño de este fragmento utilizando el enlace de datos.
        binding = FragmentRecetaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura los elementos de la interfaz de usuario con los detalles de la receta.
        binding.txtRecetaName.text = receta.strMeal
        binding.txtRecetaCategory.text = receta.strCategory
        binding.txtRecetaLugar.text = receta.strArea
        binding.txtDetalleReceta.text = receta.strInstructions

        // Carga la imagen de la receta si se proporciona una URL, de lo contrario, muestra una imagen de prueba.
        if (receta.strMealThumb.isNotEmpty()) {
            binding.imageReceta.load(receta.strMealThumb)
        } else {
            binding.imageReceta.setImageResource(R.drawable.prueba_image)
        }

        // Configura el botón de favoritos y su comportamiento cuando se hace clic.
        setImage()
        binding.btnFavorite.setOnClickListener {
            if (receta.isFavorite) {
                // Si ya es un favorito, se elimina de la base de datos.
                receta.isFavorite = false
                viewModel.deleteRecetas(receta)
                setImage()
            } else {
                // Si no es un favorito, se agrega a la base de datos.
                receta.isFavorite = true
                viewModel.addToFavorites(receta)
                setImage()
            }
        }
    }

    private fun setImage() {
        // Configuración del icono del botón de favoritos según si la receta es un favorito o no.
        if (receta.isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}