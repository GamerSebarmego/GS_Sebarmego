package com.pe.edu.idat.grupo2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.FragmentListarApiBinding

class ListarApiFragment : Fragment() {
    private lateinit var binding: FragmentListarApiBinding
    private lateinit var viewModel: ListApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa el ViewModel asociado a este fragmento.
        viewModel = ViewModelProvider(requireActivity())[ListApiViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño de este fragmento utilizando el enlace de datos.
        binding = FragmentListarApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Crea un adaptador para mostrar la lista de recetas y define una acción de clic para navegar a los detalles de la receta.
        val adapter = RVRecetaAdapter(listOf()) { receta ->
            // Crea una dirección de navegación para ir al fragmento de detalles de la receta seleccionada.
            val direction = ListarApiFragmentDirections.actionListarApiFragmentToRecetaDetailFragment(receta)
            // Obtiene el controlador de navegación y realiza la navegación al fragmento de detalles.
            findNavController().navigate(direction)
        }
        // Establece el adaptador en el RecyclerView.
        binding.rvRecetas.adapter = adapter

        // Establece el diseño del RecyclerView como un GridLayout con 2 columnas.
        binding.rvRecetas.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        // Observa los cambios en la lista de recetas en el ViewModel y actualiza el adaptador cuando cambia.
        viewModel.recetas.observe(requireActivity()) { recetas ->
            adapter.recetas = recetas
            adapter.notifyDataSetChanged()
        }

        // Llama a la función en el ViewModel para obtener las recetas desde el servicio.
        viewModel.getRecetaFromService()
    }
}