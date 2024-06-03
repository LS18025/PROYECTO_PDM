package com.example.enviorapido_pdm.ui.transportista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.enviorapido_pdm.ui.BaseDatos.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R
import java.util.UUID
import kotlin.math.absoluteValue

class CrearTransportista : AppCompatActivity() {
    // CONEXION A DB
    private lateinit var dbHelper: ConexionDataBaseHelper
    // CONTROLES DE FORMULARIO
    private var idTransportista: Int = -1
    private lateinit var txtNomb: EditText
    private lateinit var txtApell: EditText
    private lateinit var txtNumTel: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_transportista)
        idTransportista = intent.getIntExtra("ID_TRANSPORTISTA", -1)

        // Inicializamos el helper de la base de datos
        dbHelper = ConexionDataBaseHelper(this)

        txtNomb = findViewById(R.id.editNombreTrans)
        txtApell = findViewById(R.id.editTextApellidoTrans)
        txtNumTel = findViewById(R.id.editTextTel)

        val btnGuardar: Button = findViewById(R.id.buttonRegistrarTrans)

        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                guardarTransportistaEnDB()
            }
        }
    }

    private fun validarCampos(): Boolean {
        val nombreTrans = txtNomb.text.toString()
        val apellidoTrans = txtApell.text.toString()
        val numeroTrans = txtNumTel.text.toString()

        if (nombreTrans.isEmpty()) {
            Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            return false
        }

        if (apellidoTrans.isEmpty()) {
            Toast.makeText(this, "El apellido no puede estar vacío", Toast.LENGTH_SHORT).show()
            return false
        }

        if (numeroTrans.isEmpty()) {
            Toast.makeText(this, "El número de teléfono no puede estar vacío", Toast.LENGTH_SHORT).show()
            return false
        }
        val phonePattern = Regex("^\\d{8}\$|^\\d{4}-\\d{4}\$")
        if (!phonePattern.matches(numeroTrans)) {
            Toast.makeText(this, "El número de teléfono no es válido", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun guardarTransportistaEnDB() {
        val idTransportista = UUID.randomUUID().hashCode().absoluteValue.toString().take(8).toInt()
        val nombreTrans = txtNomb.text.toString()
        val apellidoTrans = txtApell.text.toString()
        val numeroTrans = txtNumTel.text.toString()

        val IdResultado = dbHelper.AgregarTransportistas(idTransportista, nombreTrans, apellidoTrans, numeroTrans)

        if (IdResultado != -1L) {
            Toast.makeText(this, "Transportista agregado con éxito", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad y regresa a la actividad anterior
        } else {
            Toast.makeText(this, "Hubo un error al agregar el transportista", Toast.LENGTH_SHORT).show()
        }
    }
}
