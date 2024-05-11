package com.example.enviorapido_pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import kotlin.math.absoluteValue
import android.app.DatePickerDialog
import android.widget.EditText
import java.util.Calendar
class RegistrarEnvio : AppCompatActivity() {

    private lateinit var dbHelper: ConexionDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_envio)

        //Inicializamos el helper de la base de datos
        dbHelper = ConexionDataBaseHelper(this)

        //Obtenemos referencias a los elementos de la interfaz de usuario
        val editTextFechaProgramada : EditText = findViewById(R.id.editTextFechaProgramada) // Cambiado a EditText
        val buttonRegistrarEnvio : Button = findViewById(R.id.buttonRegistrarEnvio)
        val spinnerDireccion : Spinner = findViewById(R.id.spinnerDireccion)
        val spinnerDestinatario : Spinner = findViewById(R.id.spinnerDestinatario)
        val spinnerTransportista : Spinner = findViewById(R.id.spinnerTransportista)

        //Configuramos el click listener para el EditText de fecha programada
        editTextFechaProgramada.setOnClickListener {
            mostrarDatePickerDialog(editTextFechaProgramada)
        }

        //Configuramos los adaptadores para los Spinner
        configurarAdaptadores(spinnerDireccion,dbHelper.RecuperarTodaslasDirecciones()){
            direccion -> direccion.direccion
        }
        configurarAdaptadores(spinnerDestinatario, dbHelper.RecuperarTodoslosDestinatarios()){
            destinatarios -> destinatarios.nombre_Destinatario + " " + destinatarios.apellido_Destinatario
        }

        configurarAdaptadores(spinnerTransportista, dbHelper.recuperarTodosLosTransportistas()){
            transportista -> transportista.nombreTransportista + " " + transportista.apellidoTransportista
        }

        //Configuramos el click listener para el boton de registro

        buttonRegistrarEnvio.setOnClickListener() {

            //Obtenemos los valores de los campos
            val direccionSeleccionadaIndex = spinnerDireccion.selectedItemPosition
            val direccionSeleccionada = dbHelper.RecuperarTodaslasDirecciones()[direccionSeleccionadaIndex]
            val idDireccion = direccionSeleccionada.id_direccion

            val destinatarioSeleccionadoIndex = spinnerDestinatario.selectedItemPosition
            val destinatarioSeleccionado = dbHelper.RecuperarTodoslosDestinatarios()[destinatarioSeleccionadoIndex]
            val idDestinatario = destinatarioSeleccionado.id_Destinatario

            val transportistaSeleccionadoIndex = spinnerTransportista.selectedItemPosition
            val transportistaSeleccionado = dbHelper.recuperarTodosLosTransportistas()[transportistaSeleccionadoIndex]
            val idTransportista = transportistaSeleccionado.idTransportista

            val costoTotalEnvio = 5.0
            val fechaProgramada = obtenerFechaProgramada(editTextFechaProgramada)

            val firebaseAuth = FirebaseAuth.getInstance()
            val currentUser = firebaseAuth.currentUser
            val idUsuario = currentUser?.uid ?: ""

            //Generamos un ID unico para el envio
            val idEnvio = UUID.randomUUID().hashCode().absoluteValue.toString().take(8).toInt()

            //Generamos un numero de confirmacion unico para el envio
            val numeroConfi = UUID.randomUUID().hashCode().absoluteValue.toString().take(5)

            //Generamos un numero de etiqueta unico para el envio
            val numetiqueta = UUID.randomUUID().hashCode().absoluteValue.toString().take(5)

            //Llamamos al metodo para agregar el envio a la base de datos
            val idResultado = dbHelper.AgregarEnvio(
                idEnvio,
                idUsuario,
                idDireccion,
                idDestinatario,
                idTransportista,
                numetiqueta,
                costoTotalEnvio,
                obtenerFechaHoy(),
                fechaProgramada,
                numeroConfi
            )

            //Mostrar un mensaje dependiendo del resultado de la insercion

            if (idResultado != -1L)
            {
                Toast.makeText(this,"Envio registrado con exito",Toast.LENGTH_SHORT).show()

                val i = Intent (this, MainActivity::class.java)
                startActivity(i)

            } else {
                Toast.makeText(this,"Error al registrar el Envio",Toast.LENGTH_SHORT).show()
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