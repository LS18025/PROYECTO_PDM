package com.example.enviorapido_pdm.ui.transportista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R
import androidx.appcompat.widget.AppCompatTextView
class CrearTransportista : AppCompatActivity() {
    //CONEXION A DB
    private lateinit var dbHelper: ConexionDataBaseHelper
    //CONTROLES DE FORMULARIO
    private lateinit var txtId: TextView
    private lateinit var txtNomb: TextView
    private lateinit var txtApell: TextView
    private lateinit var txtNumTel: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_transportista)

        //Inicializamos el helper de la base de datos
        dbHelper = ConexionDataBaseHelper(this)

        val btnGuardar: Button = findViewById(R.id.buttonRegistrarTrans)

        btnGuardar.setOnClickListener() {
            Toast.makeText(this, "Guardando...", Toast.LENGTH_SHORT).show()
            guardarTransportistaEnDB()

        }

    }
    private fun guardarTransportistaEnDB()
    {
        txtId = findViewById(R.id.txtIdTrans)
        txtNomb = findViewById(R.id.editNombreTrans)
        txtApell = findViewById(R.id.editTextApellidoTrans)
        txtNumTel = findViewById(R.id.editTextTel)

        val IdResultado = dbHelper.AgregarTransportistas(txtId.text.toString().toInt(),txtNomb.text.toString(),txtApell.text.toString(),txtNumTel.text.toString())

        if(IdResultado==-1.toLong())
        {
            Toast.makeText(this,"Hubo un error o el codigo del transportista ya existe", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"Transportista agregado con exito", Toast.LENGTH_SHORT).show()
        }

    }
}