package com.example.enviorapido_pdm

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enviorapido_pdm.ui.transportista.CrearTransportista
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R
import android.app.AlertDialog
import android.content.DialogInterface


class VistaTransportista : AppCompatActivity(), TransportistaAdapter.OnItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransportistaAdapter
    private lateinit var dbHelper: ConexionDataBaseHelper
    private var selectedTransportista: Transportista? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_transportista)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = ConexionDataBaseHelper(this)
        adapter = TransportistaAdapter(dbHelper.recuperarTodosLosTransportistas() as ArrayList<Transportista>, this)
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
                val idTransportista = selectedTransportista!!.idTransportista

                // Mostrar un cuadro de diálogo de confirmación antes de eliminar
                AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Estás seguro de que quieres eliminar este transportista?")
                    .setPositiveButton("Sí") { dialog, which ->
                        // Si el usuario confirma, procede con la eliminación
                        val filasAfectadas = dbHelper.eliminarTransportista(idTransportista)
                        if (filasAfectadas > 0) {
                            // Eliminación exitosa, actualiza la lista de transportistas
                            adapter.listaTransportistas = dbHelper.recuperarTodosLosTransportistas() as ArrayList<Transportista>
                            adapter.notifyDataSetChanged()
                            selectedTransportista = null // Limpia la selección
                        } else {
                            // Error al eliminar, muestra un mensaje de error
                            Toast.makeText(this, "Error al eliminar el transportista", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("No", null) // Si el usuario cancela, no hace nada
                    .show()
            } else {
                // No hay transportista seleccionado, muestra un mensaje de advertencia
                Toast.makeText(this, "Selecciona un transportista para eliminar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualiza la lista de transportistas
        adapter.listaTransportistas = dbHelper.recuperarTodosLosTransportistas() as ArrayList<Transportista>
        adapter.notifyDataSetChanged()
    }

    override fun onItemSelected(transportista: Transportista) {
        selectedTransportista = transportista
    }
}