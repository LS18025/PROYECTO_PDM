package com.example.enviorapido_pdm

import SeguimientoAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListarSeguimientos : AppCompatActivity() {
    private lateinit var dbHelper: ConexionDataBaseHelper
    private lateinit var seguimientoAdapter: SeguimientoAdapter

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_seguimientos)

        dbHelper = ConexionDataBaseHelper(this)

        val idEnvio = intent.getIntExtra("ENVIO_ID", -1) // Aquí se recibe el ID de envío
        Log.d("ListarSeguimientos", "ID de Envío: $idEnvio")
        val buttonAgregarSeguimiento : Button = findViewById(R.id.buttonAgregarSeguimiento)
        val seguimientos = dbHelper.recuperarSeguimientoPorIdEnvio(idEnvio) // Se usa el ID de envío para recuperar los seguimientos

        val listViewSeguimientos: ListView = findViewById(R.id.listViewSeguimientos)
        seguimientoAdapter = SeguimientoAdapter(this, seguimientos)
        listViewSeguimientos.adapter = seguimientoAdapter

        //Redirigir a la actividad de CrearSeguimiento
        buttonAgregarSeguimiento.setOnClickListener {
            val intent = Intent(this, CrearSeguimiento::class.java)
            intent.putExtra("ENVIO_ID", idEnvio) // Asegúrate de pasar el ID de envío aquí
            startActivity(intent)
        }
    }
}
