package com.example.enviorapido_pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AgregarPaquete : AppCompatActivity() {

    private lateinit var dbHelper:ConexionDataBaseHelper

    private lateinit var editIdPaquete: EditText
    private lateinit var editIdEnvioPaquete: EditText
    private lateinit var editCostoPaquete: EditText
    private lateinit var editPesoPaquete: EditText
    private lateinit var editTamanoPaquete: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_paquete)

        dbHelper = ConexionDataBaseHelper(this)

        editIdPaquete = findViewById(R.id.editIdPaquete)
        editIdEnvioPaquete = findViewById(R.id.editIdEnvioPaquete)
        editCostoPaquete = findViewById(R.id.editCostoPaquete)
        editPesoPaquete = findViewById(R.id.editPesoPaquete)
        editTamanoPaquete = findViewById(R.id.editTamanoPaquete)

        val btnGuardar: Button = findViewById(R.id.btnPaquete)

        btnGuardar.setOnClickListener {
            guardarPaqueteEnDB()
        }
    }
    private fun guardarPaqueteEnDB() {
        val idPaquete = editIdPaquete.text.toString().toInt()
        val idEnvio = editIdEnvioPaquete.text.toString().toInt()
        val costoPaquete = editCostoPaquete.text.toString().toDouble()
        val pesoPaquete = editPesoPaquete.text.toString().toDouble()
        val tamanoPaquete = editTamanoPaquete.text.toString().toDouble()

        val IdResultado = dbHelper.AgregarPaquete(idPaquete, idEnvio, costoPaquete, pesoPaquete, tamanoPaquete)

        if (IdResultado != -1L) {
            Toast.makeText(this, "Paquete agregado con éxito", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Hubo un error al agregar el paquete", Toast.LENGTH_SHORT).show()
        }
    }
}