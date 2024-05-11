package com.example.enviorapido_pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VistaPaquete : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PaqueteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_paquete)

        recyclerView = findViewById(R.id.listaPaquete)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PaqueteAdapter(ConexionDataBaseHelper(this).RecuperarTodoslosPaquetes())
        recyclerView.adapter = adapter
    }
}