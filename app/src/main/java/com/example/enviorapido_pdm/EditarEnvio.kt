package com.example.enviorapido_pdm

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class EditarEnvio : AppCompatActivity() {

    private lateinit var dbHelper: ConexionDataBaseHelper
    private var envioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_envio)

        // Inicializamos el helper de la base de datos
        dbHelper = ConexionDataBaseHelper(this)

        // Obtener el ID del envío de los extras del Intent
        envioId = intent.getIntExtra("ID_ENVIO", 0)

        // Obtener referencias a los elementos de la interfaz de usuario
        val editTextDireccion: EditText = findViewById(R.id.editTextDireccion)
        val editTextDestinatario: EditText = findViewById(R.id.editTextDestinatario)
        val editTextFechaProgramada: EditText = findViewById(R.id.editTextFechaProgramada)
        val buttonEditarEnvio: Button = findViewById(R.id.buttonEditarEnvio)
        val spinnerTransportista: Spinner = findViewById(R.id.spinnerTransportista)

        // Obtener los detalles del envío desde la base de datos y rellenar los campos
        val envio = dbHelper.RecuperarEnvioPorId(envioId)

        editTextDireccion.setText(envio?.direccion)
        editTextDestinatario.setText(envio?.destinatario)

        // Convertir el campo fechaProgramada a objeto Date
        val fechaProgramadaDate = envio?.fechaProgramada?.let { SimpleDateFormat("dd-MM-yyyy").parse(it) }

        // Formatear la fecha si no es nula
        val fechaProgramadaText = fechaProgramadaDate?.let { SimpleDateFormat("dd-MM-yyyy").format(it) } ?: ""
        editTextFechaProgramada.setText(fechaProgramadaText)

        // Configurar adaptadores para el Spinner de transportista
        val transportistas = dbHelper.recuperarTodosLosTransportistas()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, transportistas.map { it.nombreTransportista })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTransportista.adapter = adapter

        // Seleccionar el transportista actual del envío
        val transportistaActualIndex = transportistas.indexOfFirst { it.idTransportista == envio?.idTransportista }
        spinnerTransportista.setSelection(transportistaActualIndex)

        // Configurar el OnClickListener para el EditText de la fecha programada
        editTextFechaProgramada.setOnClickListener {
            mostrarDatePickerDialog(editTextFechaProgramada)
        }

        // Configurar el click listener para el botón de editar
        // Dentro del OnClickListener del botón de editar
        buttonEditarEnvio.setOnClickListener {
            // Obtener los valores de los campos
            val direccionIngresada = editTextDireccion.text.toString()
            val destinatarioIngresado = editTextDestinatario.text.toString()
            val fechaProgramada = obtenerFechaProgramada(editTextFechaProgramada)
            val transportistaSeleccionado = transportistas[spinnerTransportista.selectedItemPosition]

            // Actualizar el envío en la base de datos
            val resultado = dbHelper.actualizarEnvio(
                envioId,
                direccionIngresada,
                destinatarioIngresado,
                fechaProgramada,
                transportistaSeleccionado.idTransportista
            )

            // Verificar si la actualización fue exitosa
            if (resultado > 0) {
                Toast.makeText(this, "Envío actualizado con éxito", Toast.LENGTH_SHORT).show()
                // Devolver el resultado a la actividad anterior
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar el envío", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun mostrarDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, monthOfYear)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                editText.setText(sdf.format(selectedDate.time))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun obtenerFechaProgramada(editText: EditText): Date {
        val dateString = editText.text.toString()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.parse(dateString)
    }
}
