package com.example.enviorapido_pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnAcceder : Button = findViewById(R.id.btnAcceder)
        val txtusername : TextView = findViewById(R.id.txtusername)
        val txtcontraseña : TextView = findViewById(R.id.txtContraseña)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnOlvidar: TextView = findViewById(R.id.btnOlvidar)

        firebaseAuth = Firebase.auth

        btnAcceder.setOnClickListener() {

            signIn(txtusername.text.toString(),txtcontraseña.text.toString())

        }
        btnRegistrar.setOnClickListener(){
            val i = Intent (this,RegistrarCuenta::class.java)
            startActivity(i)
        }
        btnOlvidar.setOnClickListener()
        {
            val i = Intent (this,RecordarContrasena::class.java)
            startActivity(i)
        }

    }

    private fun signIn(email: String, password:String)
    {
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val user = firebaseAuth.currentUser
                    Toast.makeText(baseContext,"Autenticacion Exitosa",Toast.LENGTH_SHORT).show()

                    //Vamos hacia el Dashboard

                    val i = Intent(this,MainActivity::class.java)
                    startActivity(i)
                }
                else{
                    Toast.makeText(baseContext,"Error en las credenciales",Toast.LENGTH_SHORT).show()
                }
            }
    }
}