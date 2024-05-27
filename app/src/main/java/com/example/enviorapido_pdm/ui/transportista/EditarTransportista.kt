package com.example.enviorapido_pdm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class EditarTransportista : AppCompatActivity() {

    private lateinit var txtEditIdTransportista: EditText
    private lateinit var txtEditNombreTransportista: EditText
    private lateinit var txtEditApellidoTransportista: EditText
    private lateinit var txtEditTelefonoTransportista: EditText
    private lateinit var btnActualizarTransportista: Button

    private lateinit var dbHelper: ConexionDataBaseHelper
    private var idTransportista: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_transportista)

        // Inicializar vistas
        txtEditIdTransportista = findViewById(R.id.txtEditIdTransportista)
        txtEditNombreTransportista = findViewById(R.id.txtEditNombreTransportista)
        txtEditApellidoTransportista = findViewById(R.id.txtEditApellidoTransportista)
        txtEditTelefonoTransportista = findViewById(R.id.txtEditTelefonoTransportista)
        btnActualizarTransportista = findViewById(R.id.btnActualizarTransportista)

        // Inicializar DBHelper
        dbHelper = ConexionDataBaseHelper(this)

        // Obtener ID del transportista a editar de los extras
        idTransportista = intent.getIntExtra("id_transportista", -1)

        // Cargar datos del transportista a editar
        cargarDatosTransportista(idTransportista)

        // Configurar el click listener para el botón de actualización
        btnActualizarTransportista.setOnClickListener {
            // Obtener los nuevos valores de los campos de texto
            val nombre = txtEditNombreTransportista.text.toString()
            val apellido = txtEditApellidoTransportista.text.toString()
            val telefono = txtEditTelefonoTransportista.text.toString()

            // Actualizar el transportista en la base de datos
            val filasAfectadas = dbHelper.ActualizarTransportista(idTransportista, nombre, apellido, telefono)

            if (filasAfectadas > 0) {
                Toast.makeText(this, "Transportista actualizado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "No se pudo actualizar el transportista", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarDatosTransportista(idTransportista: Int) {
        val transportista = dbHelper.RecuperarTransportistaPorId(idTransportista)

        if (transportista != null) {
            txtEditIdTransportista.setText(transportista.idTransportista.toString())
            txtEditNombreTransportista.setText(transportista.nombreTransportista)
            txtEditApellidoTransportista.setText(transportista.apellidoTransportista)
            txtEditTelefonoTransportista.setText(transportista.telefonoTransportista)
        } else {
            Toast.makeText(this, "No se encontró ningún transportista con el ID $idTransportista", Toast.LENGTH_SHORT).show()
        }
    }
}
