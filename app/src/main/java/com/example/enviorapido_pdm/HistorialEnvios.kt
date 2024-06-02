package com.example.enviorapido_pdm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class HistorialEnvios : AppCompatActivity() {

    private lateinit var dbHelper: ConexionDataBaseHelper
    private lateinit var recyclerViewEnvios: RecyclerView
    private lateinit var enviosAdapter: EnviosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_envios)

        dbHelper = ConexionDataBaseHelper(this)
        recyclerViewEnvios = findViewById(R.id.recyclerViewEnvios)
        recyclerViewEnvios.layoutManager = LinearLayoutManager(this)

        // Obtener el usuario actual de Firebase
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val idUsuario = currentUser?.uid ?: ""

        // Recuperar los envíos del usuario actual
        val envios = dbHelper.RecuperarEnviosPorUsuario(idUsuario)

        // Configurar el adaptador para mostrar los envíos en el RecyclerView
        enviosAdapter = EnviosAdapter(this, envios)
        recyclerViewEnvios.adapter = enviosAdapter
    }
}
