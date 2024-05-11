package com.example.enviorapido_pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VerPerfilUsuario : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var txtNombre: TextView
    private lateinit var txtApellido: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtUsuario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_perfil_usuario)

        txtNombre = findViewById(R.id.textView71)
        txtApellido = findViewById(R.id.textView72)
        txtCorreo = findViewById(R.id.textView73)
        txtUsuario = findViewById(R.id.textView74)

        //Inicializamos la instancia de FirebaseAuth
        firebaseAuth =  FirebaseAuth.getInstance()

        //Obtenemos el ID del usuario actual
        val currentUser = firebaseAuth.currentUser
        val uid = currentUser?.uid ?: ""

        //Buscar los datos del usuario en la base de datos
        val db = ConexionDataBaseHelper(this)
        val usuario = db.recuperarUsuarioPorId(uid)

        //Mostar los datos del usuario en la vista

        findViewById<TextView>(R.id.textView71).text = usuario?.primer_nombre_persona
        findViewById<TextView>(R.id.textView72).text = usuario?.primer_apellido_persona
        findViewById<TextView>(R.id.textView73).text = usuario?.email_persona
        findViewById<TextView>(R.id.textView74).text = usuario?.usuario
    }
}