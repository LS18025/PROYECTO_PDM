package com.example.enviorapido_pdm.ui.administracion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.enviorapido_pdm.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecordarContrasena : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordar_contrasena)

        val txtemail : TextView = findViewById(R.id.txtRecupEmail)
        val btnCambiar : Button = findViewById(R.id.btnRecuperarPass)

        btnCambiar.setOnClickListener()
        {
            EnviarCambioContraseña(txtemail.text.toString())
        }

        firebaseAuth = Firebase.auth
    }

    private fun EnviarCambioContraseña(email:String)
    {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(baseContext,"Correo de Cambio de contraseña enviado",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext,"Error, no se puede completar el proceso",Toast.LENGTH_SHORT).show()
                }
            }
    }
}