package com.example.enviorapido_pdm.ui.administracion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.ConsultarSeguimientoPedidos
import com.example.enviorapido_pdm.MainActivity
import com.example.enviorapido_pdm.R
import com.example.enviorapido_pdm.ui.rastreo.DatosRastreoPaqueteFragment
import com.example.enviorapido_pdm.ui.rastreo.RastrearPaqueteFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Instanciar ConexionDataBaseHelper para asegurar la creación de la base de datos y las tablas
        val db = ConexionDataBaseHelper(this)

        // Verificar si el usuario administrador ya existe
        if (!db.isAdministratorUserExists()) {
            // Insertar el usuario administrador si no existe
            db.insertAdministratorUser()
        }


        val btnAcceder: Button = findViewById(R.id.btnAcceder)
        val txtusername: TextView = findViewById(R.id.txtusername)
        val txtcontraseña: TextView = findViewById(R.id.txtContraseña)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnOlvidar: TextView = findViewById(R.id.btnOlvidar)
        val btnRastreo: ImageButton = findViewById(R.id.imagebuttonRastreo)

        firebaseAuth = Firebase.auth

        btnAcceder.setOnClickListener() {
            signIn(txtusername.text.toString(), txtcontraseña.text.toString())
        }
        btnRegistrar.setOnClickListener() {
            val i = Intent(this, RegistrarCuenta::class.java)
            startActivity(i)
        }
        btnOlvidar.setOnClickListener()
        {
            val i = Intent(this, RecordarContrasena::class.java)
            startActivity(i)
        }
        btnRastreo.setOnClickListener()
        {
            val i = Intent(this, ConsultarSeguimientoPedidos::class.java)
            startActivity(i)
        }
    }

    private fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val verifica = user?.isEmailVerified
                    if (verifica == true) {
                        val db = ConexionDataBaseHelper(this)
                        val userRole = db.getUserRole(user.uid)
                        Log.d("UserRole", "Valor de userRole: $userRole")
                        Toast.makeText(baseContext, "Autenticacion Exitosa", Toast.LENGTH_SHORT)
                            .show()

                        val i = Intent(this, MainActivity::class.java)
                        i.putExtra("USER_ROLE", userRole) // Pasar el rol del usuario como un extra
                        startActivity(i)
                    } else {
                        Toast.makeText(
                            baseContext,
                            "No ha verificado su correo electronico",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(baseContext, "Error en las credenciales", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }


}