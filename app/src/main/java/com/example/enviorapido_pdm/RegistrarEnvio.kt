package com.example.enviorapido_pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import kotlin.math.absoluteValue
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import com.example.enviorapido_pdm.ui.paquete.VistaPaquete
import java.util.Calendar

class RegistrarEnvio : AppCompatActivity() {

    private lateinit var dbHelper: ConexionDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_envio)

        // Inicializamos el helper de la base de datos
        dbHelper = ConexionDataBaseHelper(this)

        // Obtenemos referencias a los elementos de la interfaz de usuario
        val editTextDireccion: EditText = findViewById(R.id.editTextDireccion)
        val editTextDestinatario: EditText = findViewById(R.id.editTextDestinatario)
        val editTextFechaProgramada: EditText = findViewById(R.id.editTextFechaProgramada)
        val buttonRegistrarEnvio: Button = findViewById(R.id.buttonRegistrarEnvio)
        val spinnerTransportista: Spinner = findViewById(R.id.spinnerTransportista)

        // Configuramos el click listener para el EditText de fecha programada
        editTextFechaProgramada.setOnClickListener {
            mostrarDatePickerDialog(editTextFechaProgramada)
        }

        // Configuramos los adaptadores para el Spinner de transportista
        configurarAdaptadores(spinnerTransportista, dbHelper.recuperarTodosLosTransportistas()) {
                transportista -> transportista.nombreTransportista + " " + transportista.apellidoTransportista
        }

        // Configuramos el click listener para el botón de registro
        buttonRegistrarEnvio.setOnClickListener {
            // Obtenemos los valores de los campos
            val direccionIngresada = editTextDireccion.text.toString()
            val destinatarioIngresado = editTextDestinatario.text.toString()

            val transportistaSeleccionadoIndex = spinnerTransportista.selectedItemPosition
            val transportistaSeleccionado = dbHelper.recuperarTodosLosTransportistas()[transportistaSeleccionadoIndex]
            val idTransportista = transportistaSeleccionado.idTransportista

            val costoTotalEnvio = 0.0
            val fechaProgramada = obtenerFechaProgramada(editTextFechaProgramada)

            val firebaseAuth = FirebaseAuth.getInstance()
            val currentUser = firebaseAuth.currentUser
            val idUsuario = currentUser?.uid ?: ""

            // Generamos un ID único para el envío
            val idEnvio = UUID.randomUUID().hashCode().absoluteValue.toString().take(8).toInt()

            // Generamos un número de confirmación único para el envío
            val numeroConfi = UUID.randomUUID().hashCode().absoluteValue.toString().take(5)

            // Generamos un número de etiqueta único para el envío
            val numetiqueta = UUID.randomUUID().hashCode().absoluteValue.toString().take(12)

            // Llamamos al método para agregar el envío a la base de datos
            val idResultado = dbHelper.AgregarEnvio(
                idEnvio,
                idUsuario,
                direccionIngresada,
                destinatarioIngresado,
                idTransportista,
                numetiqueta,
                costoTotalEnvio,
                obtenerFechaHoy(),
                fechaProgramada,
                numeroConfi
            )

            // Mostrar un mensaje dependiendo del resultado de la inserción
            if (idResultado != -1L) {
                Toast.makeText(this, "Envío registrado con éxito", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this, VistaPaquete::class.java)
                intent.putExtra("ID_ENVIO", idEnvio)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al registrar el Envío", Toast.LENGTH_SHORT).show()
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

                val timePickerDialog = TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDate.set(Calendar.MINUTE, minute)

                        val sdf = SimpleDateFormat("dd-MM-yyyy") // Formato que incluye la hora
                        editText.setText(sdf.format(selectedDate.time))
                    },
                    calendar.get(Calendar.HOUR_OF_DAY), // Hora actual
                    calendar.get(Calendar.MINUTE), // Minuto actual
                    false
                )

                timePickerDialog.show()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


    private fun <T> configurarAdaptadores(spinner: Spinner, lista: List<T>, campoAMostrar: (T) -> String) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista.map { campoAMostrar(it) })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun obtenerFechaProgramada(editText: EditText): Date {
        val dateString = editText.text.toString()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.parse(dateString)
    }

    private fun obtenerFechaHoy(): Date {
        return Date() // Retorna la fecha actual
    }
}
