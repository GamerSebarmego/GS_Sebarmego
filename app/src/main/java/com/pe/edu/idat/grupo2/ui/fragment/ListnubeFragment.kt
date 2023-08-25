package com.pe.edu.idat.grupo2.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.FragmentListnubeBinding
import com.pe.edu.idat.grupo2.ui.AddRecetasNubeActivity

class ListnubeFragment : Fragment() {
    lateinit var binding: FragmentListnubeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño de este fragmento utilizando el enlace de datos.
        binding = FragmentListnubeBinding.inflate(inflater, container, false)

        // Establece un clic en el botón flotante (fab) para agregar una receta en la nube.
        binding.fabAddReceta.setOnClickListener {
            // Crea una intención (Intent) para abrir la actividad "AddRecetasNubeActivity".
            val intent = Intent(requireContext(), AddRecetasNubeActivity::class.java)
            // Inicia la actividad "AddRecetasNubeActivity".
            startActivity(intent)
        }

        // Devuelve la vista raíz del fragmento.
        return binding.root
    }
}