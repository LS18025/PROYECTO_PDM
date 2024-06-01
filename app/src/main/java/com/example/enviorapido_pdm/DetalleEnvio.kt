package com.example.enviorapido_pdm

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleEnvio: AppCompatActivity() {

    private lateinit var dbHelper: ConexionDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_envio)

        dbHelper = ConexionDataBaseHelper(this)

        // Obtener el ID del envío desde el Intent
        val envioId = intent.getIntExtra("ENVIO_ID", -1)
        val envio = dbHelper.RecuperarEnvioPorId(envioId)

        // Referencias a los elementos de la interfaz de usuario
        val textViewEnvioDireccion: TextView = findViewById(R.id.textViewEnvioDireccion)
        val textViewEnvioDestinatario: TextView = findViewById(R.id.textViewEnvioDestinatario)
        val textViewEnvioTransportista: TextView = findViewById(R.id.textViewEnvioTransportista)
        val textViewEnvioEtiqueta: TextView = findViewById(R.id.textViewEnvioEtiqueta)
        val textViewEnvioCosto: TextView = findViewById(R.id.textViewEnvioCosto)
        val textViewEnvioFecha: TextView = findViewById(R.id.textViewEnvioFecha)
        val textViewEnvioFechaProgramada: TextView = findViewById(R.id.textViewEnvioFechaProgramada)
        val textViewEnvioNumeroConf: TextView = findViewById(R.id.textViewEnvioNumeroConf)

        // Establecer los valores de los elementos de la interfaz de usuario
        textViewEnvioDireccion.text = envio?.direccion
        textViewEnvioDestinatario.text = envio?.destinatario
        textViewEnvioTransportista.text = envio?.idTransportista.toString()
        textViewEnvioEtiqueta.text = envio?.etiqueta
        textViewEnvioCosto.text = envio?.costoTotal.toString()
        textViewEnvioFecha.text = envio?.fechaEnvio
        textViewEnvioFechaProgramada.text = envio?.fechaProgramada
        textViewEnvioNumeroConf.text = envio?.numeroConf
    }
}
