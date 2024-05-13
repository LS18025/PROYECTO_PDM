package com.example.enviorapido_pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VistaPaquete : AppCompatActivity(), PaqueteAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PaqueteAdapter
    private lateinit var dbHelper: ConexionDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_paquete)

        recyclerView = findViewById(R.id.listaPaquete)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PaqueteAdapter(ConexionDataBaseHelper(this).RecuperarTodoslosPaquetes())
        recyclerView.adapter = adapter

        val btnAgregar: ImageButton = findViewById(R.id.btnAgregarPaquete)

        btnAgregar.setOnClickListener()
        {
             val Intent = Intent(this,AgregarPaquete::class.java)
             startActivity(Intent)
        }
    }
    override fun onItemClick(idPaquete: Int) {
        val intent = Intent(this, EditarPaquete::class.java)
        intent.putExtra("id_paquete", idPaquete)
        startActivity(intent)
    }
}