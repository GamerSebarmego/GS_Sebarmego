package com.pe.edu.idat.grupo2.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.FragmentAccountBinding
import com.pe.edu.idat.grupo2.ui.LoginActivity


class AccountFragment : Fragment() {
    // Declaración de propiedades
    private lateinit var binding: FragmentAccountBinding // Vista del fragmento
    private lateinit var sharedPreferences: SharedPreferences // Almacenamiento de preferencias
    private lateinit var firebaseAuth: FirebaseAuth // Autenticación de Firebase
    private lateinit var email: String // Almacenamiento de dirección de correo electrónico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de las propiedades en el método onCreate
        sharedPreferences = requireActivity().getSharedPreferences(LoginActivity.SESSION_PREFERENCES_KEY, Context.MODE_PRIVATE)
        email = sharedPreferences.getString(LoginActivity.EMAIL_DATA, "") ?: ""
        firebaseAuth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflación del diseño de la vista y asignación a la propiedad binding
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asignar el correo electrónico a un TextView en la vista
        binding.txtemail.text = email

        // Configuración de un ClickListener para el botón de cierre de sesión
        binding.btnLogout.setOnClickListener {
            // Borrar el correo electrónico de las preferencias compartidas
            with(sharedPreferences.edit()){
                putString(LoginActivity.EMAIL_DATA, "")
                apply()
            }

            // Cerrar sesión en Firebase
            firebaseAuth.signOut()

            // Ir a la pantalla de inicio de sesión
            goToLogin()
        }
    }

    // Función para navegar a la pantalla de inicio de sesión
    private fun goToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

}