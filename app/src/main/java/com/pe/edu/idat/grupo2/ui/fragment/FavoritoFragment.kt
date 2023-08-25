package com.pe.edu.idat.grupo2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.FragmentFavoritoBinding

class FavoritoFragment : Fragment() {
    private lateinit var binding: FragmentFavoritoBinding
    private lateinit var viewModel: FavoritoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa el ViewModel asociado a este fragmento.
        viewModel = ViewModelProvider(requireActivity())[FavoritoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño de este fragmento utilizando el enlace de datos.
        binding = FragmentFavoritoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Crea un adaptador para mostrar la lista de recetas favoritas.
        val adapter = RVRecetaAdapter(listOf()) { receta ->
            // Crea una dirección de navegación para ir al fragmento de detalles de la receta seleccionada.
            val direction = FavoritoFragmentDirections.actionFavoritoFragmentToRecetaDetailFragment(receta)
            // Obtiene el controlador de navegación y realiza la navegación al fragmento de detalles.
            findNavController().navigate(direction)
        }
        // Establece el adaptador en el RecyclerView.
        binding.rvFavorites.adapter = adapter

        // Establece el diseño del RecyclerView como un GridLayout con 2 columnas.
        binding.rvFavorites.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        // Observa los cambios en la lista de recetas favoritas en el ViewModel y actualiza el adaptador cuando cambia.
        viewModel.favorites.observe(requireActivity()) { recetas ->
            adapter.recetas = recetas
            adapter.notifyDataSetChanged()
        }

        // Llama a la función en el ViewModel para cargar la lista de recetas favoritas.
        viewModel.getFavorities() // Aquí se llama al evento para mostrar la lista de favoritos.
    }
}