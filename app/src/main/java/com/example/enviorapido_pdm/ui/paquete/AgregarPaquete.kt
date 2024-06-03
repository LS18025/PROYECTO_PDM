package com.example.enviorapido_pdm.ui.paquete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R
import java.util.UUID
import kotlin.math.absoluteValue
import java.text.NumberFormat
import java.util.Locale


class AgregarPaquete : AppCompatActivity() {

    private lateinit var dbHelper: ConexionDataBaseHelper

    private var idEnvio: Int = -1
    private lateinit var costoPaquete: EditText
    private lateinit var pesoPaquete: EditText
    private lateinit var tamanoPaquete: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_paquete)

        idEnvio = intent.getIntExtra("ID_ENVIO", -1)

        dbHelper = ConexionDataBaseHelper(this)

        costoPaquete = findViewById(R.id.txtCostoPaquete)
        pesoPaquete = findViewById(R.id.txtPesoPaquete)
        tamanoPaquete = findViewById(R.id.txtTamanoPaquete)

        val btnGuardar: Button = findViewById(R.id.btnPaquete)
        val btnCancelar: Button = findViewById(R.id.btnCancel)

        // Llamar a la función para configurar los listeners
        setupEditTextListeners()

        btnGuardar.setOnClickListener {
            guardarPaqueteEnDB()
        }
        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun setupEditTextListeners() {
        pesoPaquete.doOnTextChanged { _, _, _, _ -> calcularCosto() }
        tamanoPaquete.doOnTextChanged { _, _, _, _ -> calcularCosto() }
    }

    private fun calcularCosto() {
        val pesoPaquete = pesoPaquete.text.toString().toDoubleOrNull() ?: 0.0
        val tamanoPaquete = tamanoPaquete.text.toString().toDoubleOrNull() ?: 0.0
        val costoTotalPaquete = (pesoPaquete * 1.50) + (tamanoPaquete * 0.05)

        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US)
        val costoFormateado = formatoMoneda.format(costoTotalPaquete)

        costoPaquete.setText(costoFormateado)
    }


    private fun guardarPaqueteEnDB() {
        val idPaquete = UUID.randomUUID().hashCode().absoluteValue.toString().take(8).toInt()
        val costoPaqueteString = costoPaquete.text.toString().replace("$", "").replace(",", "").trim()
        val costoPaquete = costoPaqueteString.toDouble()
        val pesoPaquete = pesoPaquete.text.toString().toDouble()
        val tamanoPaquete = tamanoPaquete.text.toString().toDouble()

        val IdResultado = dbHelper.AgregarPaquete(idPaquete, idEnvio, costoPaquete, pesoPaquete, tamanoPaquete)

        if (IdResultado != -1L) {
            Toast.makeText(this, "Paquete agregado con éxito", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Hubo un error al agregar el paquete", Toast.LENGTH_SHORT).show()
        }
    }
}
