package com.example.enviorapido_pdm

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.Activity
import android.widget.Button
import com.example.enviorapido_pdm.ui.transportista.CrearTransportista


class VistaTransportista : AppCompatActivity(), TransportistaAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransportistaAdapter
    private lateinit var dbHelper: ConexionDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_transportista)

        recyclerView = findViewById(R.id.listaTransportista)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TransportistaAdapter(ConexionDataBaseHelper(this).recuperarTodosLosTransportistas())
        recyclerView.adapter = adapter

        val btnAgregar: ImageButton = findViewById(R.id.btnAgregarTransportista)


        btnAgregar.setOnClickListener {
            val intent = Intent(this, CrearTransportista::class.java)
            startActivity(intent)
        }


    }

    override fun onItemClick(idTransportista: Int) {
        val intent = Intent(this, EditarTransportista::class.java)
        intent.putExtra("id_transportista", idTransportista)
        startActivity(intent)
    }
}
