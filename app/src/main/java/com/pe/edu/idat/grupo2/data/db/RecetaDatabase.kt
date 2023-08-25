package com.pe.edu.idat.grupo2.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pe.edu.idat.grupo2.model.Recetas

// Define una base de datos utilizando la anotación @Database. La base de datos tendrá una entidad llamada Recetas y estará en la versión 1.
@Database(entities = [Recetas::class], version = 1)
abstract class RecetaDatabase : RoomDatabase() {
    // Declara un método abstracto llamado recetaDao() que proporcionará acceso a un DAO llamado RecetaDao.
    abstract fun recetaDao(): RecetaDao

    // Define un objeto companion que contiene funciones y propiedades estáticas.
    companion object {
        // Declara una variable privada llamada instance que almacenará la instancia de la base de datos.
        private var instance: RecetaDatabase? = null

        // Define una función estática llamada getDatabase que recibe un contexto y devuelve una instancia de RecetaDatabase.
        fun getDatabase(context: Context): RecetaDatabase {
            // Almacena la instancia actual en una variable temporal llamada temp.
            val temp = instance
            // Comprueba si temp no es nulo (es decir, si ya existe una instancia de la base de datos) y la devuelve.
            if (temp != null) {
                return temp
            }
            // Si no existe una instancia de la base de datos, se procede a crear una nueva instancia.
            synchronized(this) {
                // Utiliza Room para crear una nueva instancia de la base de datos RecetaDatabase.
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecetaDatabase::class.java,
                    "fastrecipedb"
                ).build()
                // Asigna la nueva instancia a la variable instance.
                instance = _instance
                // Devuelve la nueva instancia de la base de datos.
                return _instance
            }
        }
    }
}