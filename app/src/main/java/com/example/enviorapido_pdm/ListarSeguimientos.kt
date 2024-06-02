package com.example.enviorapido_pdm

import SeguimientoAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListarSeguimientos : AppCompatActivity() {
    private lateinit var dbHelper: ConexionDataBaseHelper
    private lateinit var seguimientoAdapter: SeguimientoAdapter
    private var idEnvio: Int = -1

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_seguimientos)

        dbHelper = ConexionDataBaseHelper(this)

        idEnvio = intent.getIntExtra("ENVIO_ID", -1) // Aquí se recibe el ID de envío
        if (idEnvio == -1) {
            Toast.makeText(this, "Error: ID de Envío no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        Log.d("ListarSeguimientos", "ID de Envío: $idEnvio")
        val buttonAgregarSeguimiento: Button = findViewById(R.id.buttonAgregarSeguimiento)

        // Inicializar la lista de seguimientos
        val listViewSeguimientos: ListView = findViewById(R.id.listViewSeguimientos)
        seguimientoAdapter = SeguimientoAdapter(this, arrayListOf())
        listViewSeguimientos.adapter = seguimientoAdapter

        //Redirigir a la actividad de CrearSeguimiento
        buttonAgregarSeguimiento.setOnClickListener {
            val intent = Intent(this, CrearSeguimiento::class.java)
            intent.putExtra("ENVIO_ID", idEnvio) // Asegúrate de pasar el ID de envío aquí
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Recuperar los seguimientos actualizados y refrescar el adaptador
        val seguimientos = dbHelper.recuperarSeguimientoPorIdEnvio(idEnvio)
        seguimientoAdapter.clear()
        seguimientoAdapter.addAll(seguimientos)
        seguimientoAdapter.notifyDataSetChanged()
    }
}
