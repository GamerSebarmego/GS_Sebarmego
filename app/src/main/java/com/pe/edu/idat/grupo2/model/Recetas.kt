package com.pe.edu.idat.grupo2.model
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Anotación que marca esta clase como una entidad de base de datos con nombre de tabla "receta".
@Entity(tableName = "receta")
// La anotación @Parcelize permite que la clase sea parcelable para pasarla entre componentes de la aplicación.
@Parcelize
data class Recetas(
    // Anotación que marca esta propiedad como clave primaria y permite la generación automática de valores.
    @PrimaryKey(autoGenerate = true)
    //@SerializedName("idMeal")
    val idMeal: Int, // Identificador único de la receta.

    @SerializedName("strMeal")
    val strMeal: String, // Nombre de la receta.

    @SerializedName("strCategory")
    val strCategory: String, // Categoría de la receta.

    @SerializedName("strArea")
    val strArea: String, // Área geográfica de la receta.

    @SerializedName("strInstructions")
    val strInstructions: String, // Instrucciones para preparar la receta.

    @SerializedName("strMealThumb")
    val strMealThumb: String, // URL de la imagen de la receta.

    var isFavorite: Boolean = false // Propiedad que indica si la receta es un favorito (valor predeterminado: false).
) : Parcelable {

}
//fun getData():List<Recetas> = listOf()