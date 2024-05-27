package com.example.enviorapido_pdm.ui.paquete

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R

class EditarPaquete : AppCompatActivity() {

    private lateinit var idEnvio: String
    private lateinit var txtEditCostoPaquete: EditText
    private lateinit var txtEditPesoPaquete: EditText
    private lateinit var txtEditTamanoPaquete: EditText
    private lateinit var btnActualizarPaquete: Button

    private lateinit var dbHelper: ConexionDataBaseHelper
    private var idPaquete: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_paquete)

        // Inicializar vistas
        txtEditCostoPaquete = findViewById(R.id.txtEditCostoPaquete)
        txtEditPesoPaquete = findViewById(R.id.txtEditPesoPaquete)
        txtEditTamanoPaquete = findViewById(R.id.txtEditTamanoPaquete)
        btnActualizarPaquete = findViewById(R.id.btnActualizarPaquete)

        // Inicializar DBHelper
        dbHelper = ConexionDataBaseHelper(this)

        // Obtener ID del paquete a editar de los extras
        idPaquete = intent.getIntExtra("id_paquete", -1)


        // Cargar datos del paquete a editar
        cargarDatosPaquete(idPaquete)

        // Configurar el click listener para el botón de actualización
        btnActualizarPaquete.setOnClickListener {
            // Obtener los nuevos valores de los campos de texto

            val costoPaquete = txtEditCostoPaquete.text.toString().toDouble()
            val pesoPaquete = txtEditPesoPaquete.text.toString().toDouble()
            val tamanoPaquete = txtEditTamanoPaquete.text.toString().toDouble()

            // Actualizar el paquete en la base de datos
            val filasAfectadas = dbHelper.ActualizarPaquete(idPaquete, idEnvio.toInt(), costoPaquete, pesoPaquete, tamanoPaquete)

            if (filasAfectadas > 0) {
                Toast.makeText(this, "Paquete actualizado correctamente", Toast.LENGTH_SHORT).show()
                finish()

            } else {
                Toast.makeText(this, "No se pudo actualizar el paquete", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun cargarDatosPaquete(idPaquete: Int) {
        val paquete = dbHelper.RecuperarPaquetePorId(idPaquete)

        if (paquete != null) {
            idEnvio= paquete.idEnvio.toString()
            txtEditCostoPaquete.setText(paquete.costoPaquete.toString())
            txtEditPesoPaquete.setText(paquete.pesoPaquete.toString())
            txtEditTamanoPaquete.setText(paquete.tamanoPaquete.toString())
        } else {
            Toast.makeText(this, "No se encontró ningún paquete con el ID $idPaquete", Toast.LENGTH_SHORT).show()
        }
    }
}