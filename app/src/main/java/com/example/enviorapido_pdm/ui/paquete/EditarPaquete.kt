package com.example.enviorapido_pdm.ui.paquete

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R
import java.text.NumberFormat
import java.util.Locale

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
        val btnCancelar: Button = findViewById(R.id.btnCancelarEdit)

        // Inicializar DBHelper
        dbHelper = ConexionDataBaseHelper(this)

        // Obtener ID del paquete a editar de los extras
        idPaquete = intent.getIntExtra("id_paquete", -1)

        // Cargar datos del paquete a editar
        cargarDatosPaquete(idPaquete)

        // Configurar los listeners para calcular el costo en tiempo real
        setupEditTextListeners()

        // Configurar el click listener para el botón de actualización
        btnActualizarPaquete.setOnClickListener {
            guardarCambiosPaquete()
        }
        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun setupEditTextListeners() {
        txtEditPesoPaquete.doOnTextChanged { _, _, _, _ -> calcularCosto() }
        txtEditTamanoPaquete.doOnTextChanged { _, _, _, _ -> calcularCosto() }
    }

    private fun calcularCosto() {
        val pesoPaquete = txtEditPesoPaquete.text.toString().toDoubleOrNull() ?: 0.0
        val tamanoPaquete = txtEditTamanoPaquete.text.toString().toDoubleOrNull() ?: 0.0
        val costoPaquete = (pesoPaquete * 1.50) + (tamanoPaquete * 0.05)

        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US)
        val costoFormateado = formatoMoneda.format(costoPaquete)

        txtEditCostoPaquete.setText(costoFormateado)
    }

    private fun guardarCambiosPaquete() {
        val costoPaqueteString = txtEditCostoPaquete.text.toString().replace("$", "").replace(",", "").trim()
        val costoPaquete = costoPaqueteString.toDouble()
        val pesoPaquete = txtEditPesoPaquete.text.toString().toDouble()
        val tamanoPaquete = txtEditTamanoPaquete.text.toString().toDouble()

        val filasAfectadas = dbHelper.ActualizarPaquete(idPaquete, idEnvio.toInt(), costoPaquete, pesoPaquete, tamanoPaquete)

        if (filasAfectadas > 0) {
            Toast.makeText(this, "Paquete actualizado correctamente", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK) // Esto indica que la actividad terminó con éxito
            finish()
        } else {
            Toast.makeText(this, "No se pudo actualizar el paquete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarDatosPaquete(idPaquete: Int) {
        val paquete = dbHelper.RecuperarPaquetePorId(idPaquete)

        if (paquete != null) {
            idEnvio = paquete.idEnvio.toString()
            txtEditCostoPaquete.setText(paquete.costoPaquete.toString())
            txtEditPesoPaquete.setText(paquete.pesoPaquete.toString())
            txtEditTamanoPaquete.setText(paquete.tamanoPaquete.toString())
        } else {
            Toast.makeText(this, "No se encontró ningún paquete con el ID $idPaquete", Toast.LENGTH_SHORT).show()
        }
    }
}