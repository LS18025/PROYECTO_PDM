package com.example.enviorapido_pdm

import SeguimientoAdapter
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListarSeguimientos : AppCompatActivity() {
    private lateinit var dbHelper: ConexionDataBaseHelper
    private lateinit var seguimientoAdapter: SeguimientoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_seguimientos)

        dbHelper = ConexionDataBaseHelper(this)

        val idEnvio = intent.getIntExtra("ENVIO_ID", -1)
        val seguimientos = dbHelper.recuperarSeguimientoPorIdEnvio(idEnvio)

        val listViewSeguimientos: ListView = findViewById(R.id.listViewSeguimientos)
        seguimientoAdapter = SeguimientoAdapter(this, seguimientos)
        listViewSeguimientos.adapter = seguimientoAdapter
    }
}
