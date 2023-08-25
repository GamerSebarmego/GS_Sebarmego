package com.pe.edu.idat.grupo2.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.ActivityLoginBinding
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    // Declaración de propiedades
    private lateinit var binding: ActivityLoginBinding // Vista de la actividad
    private lateinit var firebaseAuth: FirebaseAuth // Autenticación de Firebase
    private lateinit var googleLauncher: ActivityResultLauncher<Intent> // Lanzador de actividad para Google Sign-In

    //Cerrar sesion
    private lateinit var sharedPreferences: SharedPreferences // Almacenamiento de preferencias compartidas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de vistas y Firebase Auth
        setViews()
        firebaseAuth = Firebase.auth
        googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    authFirebaseWithGoogle(account.idToken)
                }catch (e: Exception){}
            }
        }
        //Cerrar sesion
        sharedPreferences= this.getSharedPreferences(SESSION_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val email: String=sharedPreferences.getString(EMAIL_DATA,"") ?:""
        if (email.isNotEmpty()){
            goToMain()
        }
    }

    // Función para autenticar en Firebase con Google
    private fun authFirebaseWithGoogle(idToken: String?) {
        val authCredential = GoogleAuthProvider.getCredential(idToken!!, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    val user = firebaseAuth.currentUser

                    // Almacenar el correo electrónico en preferencias compartidas
                    with(sharedPreferences.edit()){
                        putString(EMAIL_DATA, user?.email)
                        commit()
                    }

                    // Ir a la actividad principal
                    goToMain()
                }else{
                    Toast.makeText(this, "Ocurrió un Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Función para iniciar sesión con Google
    private fun signinWithGoogle() {
        val googleSigninOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleSigninOptions)
        val intent = googleClient.signInIntent
        googleLauncher.launch(intent)
    }

    // Función para registrarse en Firebase con correo y contraseña
    private fun signUpWithFirebase(email: String, password: String) {
        if (validateEmailPassword(email, password)){
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "El usuario fue creado correctamente", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "El usuario no fue creado", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this, "El email y password no son válidos", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para validar correos electrónicos y contraseñas
    private fun setViews(){
        // Validar y habilitar el botón de inicio de sesión al cambiar los campos de correo y contraseña
        binding.tilEmail.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateEmailPassword(text.toString(), binding.tilPassword.editText?.text.toString())
        }

        binding.tilPassword.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateEmailPassword(binding.tilEmail.editText?.text.toString(), text.toString())
        }

        // Configurar acciones para los botones de inicio de sesión y registro
        binding.btnLogin.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            signInWithFirebase(email, password)
        }
        binding.btnGoogle.setOnClickListener {
            signinWithGoogle()
        }
        binding.btnSingup.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            signUpWithFirebase(email, password)
        }
    }

    // Función para iniciar sesión en Firebase con correo y contraseña
    private fun signInWithFirebase(email: String, password: String){
        if (validateEmailPassword(email, password)){
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        val user = firebaseAuth.currentUser

                        // Almacenar el correo electrónico en preferencias compartidas
                        with(sharedPreferences.edit()){
                            putString(EMAIL_DATA, user?.email)
                            commit()
                        }

                        // Ir a la actividad principal
                        goToMain()
                    }else{
                        Toast.makeText(this, "Email o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this, "El email y la contraseña no son válidos", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para validar correos electrónicos y contraseñas
    private fun validateEmailPassword(email: String, password: String) : Boolean{
        val validateEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validatePassword = password.length > 6
        return validateEmail && validatePassword
    }

    // Función para ir a la actividad principal
    private fun goToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //Cerrar sesion
    companion object{
        const val SESSION_PREFERENCES_KEY = "SESSION_PREFERENCES_KEY"
        const val EMAIL_DATA = "EMAIL_DATA"
    }
}