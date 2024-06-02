package com.example.enviorapido_pdm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enviorapido_pdm.ui.paquete.Paquete
import com.example.enviorapido_pdm.ui.paquete.PaqueteAdapter
import androidx.appcompat.app.AlertDialog
import com.example.enviorapido_pdm.ui.paquete.VistaPaquete
import com.example.enviorapido_pdm.EditarEnvio

class DetalleEnvio : AppCompatActivity(), PaqueteAdapter.OnItemSelectedListener {

    private lateinit var dbHelper: ConexionDataBaseHelper
    private lateinit var paqueteAdapter: PaqueteAdapter

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
        val recyclerViewPaquetes: RecyclerView = findViewById(R.id.recyclerViewPaquetes)

        // Configurar el RecyclerView para los paquetes
        recyclerViewPaquetes.layoutManager = LinearLayoutManager(this)
        paqueteAdapter = PaqueteAdapter(ArrayList(), this)
        recyclerViewPaquetes.adapter = paqueteAdapter

        // Establecer los valores de los elementos de la interfaz de usuario
        textViewEnvioDireccion.text = envio?.direccion
        textViewEnvioDestinatario.text = envio?.destinatario
        textViewEnvioTransportista.text = envio?.idTransportista.toString()
        textViewEnvioEtiqueta.text = envio?.etiqueta
        textViewEnvioCosto.text = envio?.costoTotal.toString()
        textViewEnvioFecha.text = envio?.fechaEnvio
        textViewEnvioFechaProgramada.text = envio?.fechaProgramada
        textViewEnvioNumeroConf.text = envio?.numeroConf

        // Obtener y mostrar la lista de paquetes asociados al envío
        val paquetes = dbHelper.RecuperarPaquetesPorIdEnvio(envioId)
        paqueteAdapter.actualizarLista(paquetes)

        val btnEditarPaquetes: Button = findViewById(R.id.btnEditarPaquetes)
        val btnActualizarEstado: Button = findViewById(R.id.btnActualizarEstado)
        val btnEditarEnvio: Button = findViewById(R.id.btnEditarEnvio)
        val btnEliminarEnvio: Button = findViewById(R.id.btnEliminarEnvio)

        // Configurar clics en los botones
        btnEditarPaquetes.setOnClickListener {
            val intent = Intent(this, VistaPaquete::class.java)
            intent.putExtra("ID_ENVIO", envioId)
            startActivity(intent)
        }

        btnActualizarEstado.setOnClickListener {
            val intent = Intent(this, ListarSeguimientos::class.java)
            intent.putExtra("ENVIO_ID", envioId)
            startActivity(intent)
        }

        btnEditarEnvio.setOnClickListener {
            val intent = Intent(this, EditarEnvio::class.java)
            intent.putExtra("ID_ENVIO", envioId)
            startActivityForResult(intent, EDITAR_ENVIO_REQUEST_CODE)
        }
    }

    override fun onItemSelected(paquete: Paquete) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Detalles del Paquete")
            .setMessage("ID Paquete: ${paquete.idPaquete}\n" +
                    "ID Envío: ${paquete.idEnvio}\n" +
                    "Costo: ${paquete.costoPaquete}\n" +
                    "Peso: ${paquete.pesoPaquete}\n" +
                    "Tamaño: ${paquete.tamanoPaquete}")
            .setPositiveButton("Aceptar", null)
            .create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDITAR_ENVIO_REQUEST_CODE && resultCode == RESULT_OK) {
            // Actualizar la información del envío
            val envioId = intent.getIntExtra("ENVIO_ID", -1)
            val envio = dbHelper.RecuperarEnvioPorId(envioId)
            // Actualizar la información mostrada en la actividad
            val textViewEnvioDireccion: TextView = findViewById(R.id.textViewEnvioDireccion)
            val textViewEnvioDestinatario: TextView = findViewById(R.id.textViewEnvioDestinatario)
            val textViewEnvioFechaProgramada: TextView = findViewById(R.id.textViewEnvioFechaProgramada)
            textViewEnvioDireccion.text = envio?.direccion
            textViewEnvioDestinatario.text = envio?.destinatario
            textViewEnvioFechaProgramada.text = envio?.fechaProgramada
            // Actualizar cualquier otro elemento necesario
        }
    }

    companion object {
        private const val EDITAR_ENVIO_REQUEST_CODE = 1
    }
}
