package com.example.enviorapido_pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date

class RegistrarEnvio : AppCompatActivity() {

    private lateinit var dbHelper: ConexionDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_envio)

        //Inicializamos el helper de la base de datos
        dbHelper = ConexionDataBaseHelper(this)

        //Obtenemos referencias a los elementos de la interfaz de usuario
        val buttonRegistrarEnvio : Button = findViewById(R.id.buttonRegistrarEnvio)
        val datePickerFechaProgramada : DatePicker = findViewById(R.id.datePickerFechaProgramada)
        val spinnerDireccion : Spinner = findViewById(R.id.spinnerDireccion)
        val spinnerDestinatario : Spinner = findViewById(R.id.spinnerDestinatario)
        val spinnerTransportista : Spinner = findViewById(R.id.spinnerTransportista)

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

            val etiqueta = "etiqueta_prueba"
            val costoTotalEnvio = 5.0
            val fechaProgramada = obtenerFechaProgramada(datePickerFechaProgramada)
            val numeroConf = "numero_confirmacion_prueba"


            val firebaseAuth = FirebaseAuth.getInstance()
            val currentUser = firebaseAuth.currentUser
            val idUsuario = currentUser?.uid ?: ""


            //Llamamos al metodo para agregar el envio a la base de datos
            val idResultado = dbHelper.AgregarEnvio(
                0,
                idUsuario,
                idDireccion,
                idDestinatario,
                idTransportista,
                etiqueta,
                costoTotalEnvio,
                fechaProgramada,
                fechaProgramada,
                numeroConf
            )

            //Mostrar un mensaje dependiendo del resultado de la insercion

            if (idResultado != -1L)
            {
                Toast.makeText(this,"Envio registrado con exito",Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this,"Error al registrar el Envio",Toast.LENGTH_SHORT).show()
            }


        }


    }
    private fun <T> configurarAdaptadores(spinner: Spinner, lista: List<T>, campoAMostrar: (T) -> String) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista.map { campoAMostrar(it) })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun obtenerFechaProgramada(datePicker: DatePicker): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        // Construye la fecha utilizando SimpleDateFormat
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val dateString = "$day-${month + 1}-$year"
        val date = sdf.parse(dateString)

        return date
    }
}