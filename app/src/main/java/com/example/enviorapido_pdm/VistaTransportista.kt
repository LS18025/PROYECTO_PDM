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
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R



class VistaTransportista : AppCompatActivity(), TransportistaAdapter.OnItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransportistaAdapter
    private lateinit var dbHelper: ConexionDataBaseHelper
    private var selectedTransportista: Transportista? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_transportista)

        recyclerView = findViewById(R.id.listaTransportista)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TransportistaAdapter(ConexionDataBaseHelper(this).recuperarTodosLosTransportistas(), this)
        recyclerView.adapter = adapter

        val btnAgregar: ImageButton = findViewById(R.id.btnAgregarTransportista)
        val btnEditar: ImageButton = findViewById(R.id.btnEditarTransportista)
        val btnEliminar: ImageButton = findViewById(R.id.btnBorrarTransportista)

        btnAgregar.setOnClickListener {
            val intent = Intent(this, CrearTransportista::class.java)
            startActivity(intent)
        }
        btnEditar.setOnClickListener {
            selectedTransportista?.let {
                val intent = Intent(this, EditarTransportista::class.java)
                intent.putExtra("id_transportista", it.idTransportista)
                startActivity(intent)
            }
        }
        btnEliminar.setOnClickListener {
            if (selectedTransportista != null) {
                dbHelper = ConexionDataBaseHelper(this)
                val idTransportista = selectedTransportista!!.idTransportista
                val filasAfectadas = dbHelper.eliminarTransportista(idTransportista)
                if (filasAfectadas > 0) {
                    // Eliminación exitosa, actualiza la lista de paquetes
                    adapter.listaTransportistas.remove(selectedTransportista!!)
                    adapter.notifyDataSetChanged()
                    selectedTransportista = null // Limpia la selección
                } else {
                    // Error al eliminar, muestra un mensaje de error
                    Toast.makeText(this, "Error al eliminar el paquete", Toast.LENGTH_SHORT).show()
                }
            } else {
                // No hay paquete seleccionado, muestra un mensaje de advertencia
                Toast.makeText(this, "Selecciona un paquete para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onItemSelected(idTransportista: Transportista) {
        selectedTransportista = idTransportista

    }
}
