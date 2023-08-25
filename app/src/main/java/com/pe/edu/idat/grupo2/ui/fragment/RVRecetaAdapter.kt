package com.pe.edu.idat.grupo2.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.ItemRecetaBinding
import com.pe.edu.idat.grupo2.model.Recetas

class RVRecetaAdapter(var recetas: List<Recetas>, val onClickReceta: (Recetas) -> Unit): RecyclerView.Adapter<RecetaRV>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaRV {
        // Infla el diseño del elemento de la lista y lo asigna al ViewHolder.
        val binding = ItemRecetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecetaRV(binding, onClickReceta)
    }

    override fun getItemCount(): Int = recetas.size // Retorna la cantidad de elementos en la lista.

    override fun onBindViewHolder(holder: RecetaRV, position: Int) {
        // Llama al método "bind" del ViewHolder para asignar los datos a la vista.
        holder.bind(recetas[position])
    }
}

class RecetaRV(private val binding: ItemRecetaBinding, val onClickReceta: (Recetas) -> Unit): RecyclerView.ViewHolder(binding.root) {
    fun bind(recetas: Recetas) {
        // Asigna los datos de la receta a los elementos de la vista.
        binding.txtRecetaName.text = recetas.strMeal
        binding.txtRecetaCategory.text = recetas.strCategory
        binding.txtRecetaLugar.text = recetas.strArea

        // Carga la imagen de la receta si se proporciona una URL, de lo contrario, muestra una imagen de prueba.
        if (recetas.strMealThumb.isNotEmpty()) {
            binding.imageReceta.load(recetas.strMealThumb)
        } else {
            binding.imageReceta.setImageResource(R.drawable.prueba_image)
        }

        // Configura el icono del botón de favoritos según si la receta es un favorito o no.
        if (recetas.isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }

        // Configura un clic en el elemento de la lista y ejecuta la acción "onClickReceta" cuando se hace clic.
        binding.root.setOnClickListener {
            onClickReceta(recetas)
        }
    }
}