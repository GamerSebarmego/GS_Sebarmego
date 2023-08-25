package com.pe.edu.idat.grupo2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.pe.edu.idat.grupo2.R
import com.google.firebase.firestore.ktx.firestore
import androidx.activity.contextaware.withContextAvailable
import com.google.firebase.ktx.Firebase
import com.pe.edu.idat.grupo2.databinding.ActivityAddRecetasNubeBinding

class AddRecetasNubeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecetasNubeBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la instancia de FirebaseFirestore.
        firestore = Firebase.firestore

        // Infla el diseño de la actividad utilizando el enlace de datos.
        binding = ActivityAddRecetasNubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura un clic en el botón para registrar una nueva receta.
        binding.btnRegisterReceta.setOnClickListener {
            val mael = binding.tilStrMael.editText?.text.toString()
            val category = binding.tilStrCategory.editText?.text.toString()
            val area = binding.tilStrArea.editText?.text.toString()
            val instruction = binding.tilStrInstructions.editText?.text.toString()
            val image = binding.tilStrimage.editText?.text.toString()
            val isFavorite = binding.switchFavorite.isChecked

            // Verifica que los campos obligatorios no estén vacíos antes de agregar la receta.
            if (mael.isNotEmpty() && category.isNotEmpty() && area.isNotEmpty() && instruction.isNotEmpty() && image.isNotEmpty()) {
                // Llama a la función para agregar la receta a Firestore.
                addRecetaToFirestore(mael, category, area, instruction, image, isFavorite)
            }
        }

        // Llama a la función para obtener recetas favoritas de Firestore.
        getRecetas()
    }

    // Función para agregar una receta a Firestore.
    private fun addRecetaToFirestore(
        mael: String,
        category: String,
        area: String,
        instruction: String,
        image: String,
        favorite: Boolean
    ) {
        val receta = hashMapOf<String, Any>(
            "mael" to mael,
            "category" to category,
            "area" to area,
            "instruction" to instruction,
            "isFavorite" to favorite,
            "image" to image
        )

        // Agrega la receta al conjunto de datos "Recetas" en Firestore.
        firestore.collection("Recetas").add(receta)
            .addOnSuccessListener {
                // Muestra un mensaje de éxito si la receta se agrega correctamente.
                Toast.makeText(this,"Receta Agregada: ${it.id}",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                // Muestra un mensaje de error si ocurre un problema al agregar la receta.
                Toast.makeText(this, "Ocurrió un error", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para obtener recetas favoritas de Firestore.
    private fun getRecetas() {
        firestore.collection("Recetas").whereEqualTo("isFavorite", true).get()
            .addOnSuccessListener {
                // Obtiene una lista de documentos de recetas favoritas y muestra sus identificadores en el registro.
                val recetas = it.documents
                for (document in recetas){
                    Log.d("Recetas",document.id)
                }
            }
            .addOnFailureListener{}
    }
}