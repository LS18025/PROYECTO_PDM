package com.example.enviorapido_pdm

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class CrearSeguimiento : AppCompatActivity() {
    private lateinit var dbHelper: ConexionDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_seguimiento)

        dbHelper = ConexionDataBaseHelper(this)

        val idEnvio = intent.getIntExtra("ENVIO_ID", -1)
        if (idEnvio == -1) {
            Toast.makeText(this, "Error: ID de Envío no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val editTextFecha: EditText = findViewById(R.id.editTextFecha)
        val editTextEstado: EditText = findViewById(R.id.editTextEstado)
        val editTextUbicacion: EditText = findViewById(R.id.editTextUbicacion)
        val buttonAgregarSeguimiento: Button = findViewById(R.id.buttonAgregarSeguimiento)

        // Establecer la fecha actual en el EditText
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        editTextFecha.setText(currentDate)

        buttonAgregarSeguimiento.setOnClickListener {
            val fechaSeguimiento = editTextFecha.text.toString()
            val estadoSeguimiento = editTextEstado.text.toString()
            val ubicacionSeguimiento = editTextUbicacion.text.toString()

            if (fechaSeguimiento.isNotEmpty() && estadoSeguimiento.isNotEmpty() && ubicacionSeguimiento.isNotEmpty()) {
                val fecha = dateFormat.parse(fechaSeguimiento)
                val idResultado = dbHelper.agregarSeguimiento(idEnvio, fecha, estadoSeguimiento, ubicacionSeguimiento)
                if (idResultado != -1L) {
                    Toast.makeText(this, "Seguimiento agregado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar seguimiento", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
