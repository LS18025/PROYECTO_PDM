package com.example.enviorapido_pdm.ui.paquete

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.DetalleEnvio
import com.example.enviorapido_pdm.R

class VistaPaquete : AppCompatActivity(), PaqueteAdapter.OnItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PaqueteAdapter
    private var idEnvio: Int = -1
    private lateinit var dbHelper: ConexionDataBaseHelper

    private var selectedPaquete: Paquete? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            initializeDbHelper()
            if (requestCode == AGREGAR_PAQUETE_REQUEST_CODE || requestCode == EDITAR_PAQUETE_REQUEST_CODE) {
                updatePaquetesList()
            }
        }
    }

    private fun initializeDbHelper() {
        if (!::dbHelper.isInitialized) {
            dbHelper = ConexionDataBaseHelper(this)
        }
    }

    private fun updatePaquetesList() {
        adapter.listaPaquete.clear()
        adapter.listaPaquete.addAll(dbHelper.RecuperarPaquetesPorIdEnvio(idEnvio))
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_paquete)

        idEnvio = intent.getIntExtra("ID_ENVIO", -1)

        recyclerView = findViewById(R.id.listaPaquete)
        recyclerView.layoutManager = LinearLayoutManager(this)

        initializeDbHelper()

        adapter = PaqueteAdapter(ArrayList(), this)
        recyclerView.adapter = adapter

        updatePaquetesList()

        val btnAgregar: ImageButton = findViewById(R.id.btnAgregarPaquete)
        val btnEditar: ImageButton = findViewById(R.id.btnEditarPaquete)
        val btnEliminar: ImageButton = findViewById(R.id.btnBorrarPaquete)
        val buttonFinalizar: Button = findViewById(R.id.buttonFinalizar)

        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarPaquete::class.java)
            intent.putExtra("ID_ENVIO", idEnvio)
            startActivityForResult(intent, AGREGAR_PAQUETE_REQUEST_CODE)
        }

        btnEditar.setOnClickListener {
            selectedPaquete?.let {
                val intent = Intent(this, EditarPaquete::class.java)
                intent.putExtra("id_paquete", it.idPaquete)
                startActivityForResult(intent, EDITAR_PAQUETE_REQUEST_CODE)
            }
        }

        btnEliminar.setOnClickListener {
            if (selectedPaquete != null) {
                dbHelper = ConexionDataBaseHelper(this)
                val idPaquete = selectedPaquete!!.idPaquete
                val filasAfectadas = dbHelper.EliminarPaquete(idPaquete)
                if (filasAfectadas > 0) {
                    adapter.listaPaquete.remove(selectedPaquete!!)
                    adapter.notifyDataSetChanged()
                    selectedPaquete = null
                } else {
                    Toast.makeText(this, "Error al eliminar el paquete", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Selecciona un paquete para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        buttonFinalizar.setOnClickListener {
            val intent = Intent(this, DetalleEnvio::class.java)
            intent.putExtra("ENVIO_ID", idEnvio)
            startActivity(intent)
        }
    }

    override fun onItemSelected(paquete: Paquete) {
        selectedPaquete = paquete
    }

    companion object {
        const val AGREGAR_PAQUETE_REQUEST_CODE = 2
        const val EDITAR_PAQUETE_REQUEST_CODE = 1
    }
}
