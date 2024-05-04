package com.example.enviorapido_pdm.ui.departamentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R

class VistaInsertarDepartamento : AppCompatActivity() {

    //CONEXION A DB
    private lateinit var dbHelper:ConexionDataBaseHelper

    //CONTROLES DE FORMULARIO
    private lateinit var txtId:EditText
    private lateinit var txtNombre:EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_departamento)

        dbHelper = ConexionDataBaseHelper(this)

        val btnGuardar: Button = findViewById(R.id.button_depa)


        btnGuardar.setOnClickListener()
        {
            Toast.makeText(this,"Guardando..",Toast.LENGTH_SHORT).show()
            guardarDepartamentoEnDB()
        }
    }

    private fun guardarDepartamentoEnDB()
    {

        txtId = findViewById(R.id.txtIdDepa)
        txtNombre = findViewById(R.id.txtNombreDepa)

        val IdResultado = dbHelper.AgregarDepartamentos(txtId.text.toString().toInt(),txtNombre.text.toString())

        if(IdResultado==-1.toLong())
        {
            Toast.makeText(this,"Hubo un error o el codigo del departamento ya existe",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"Producto agregado con exito", Toast.LENGTH_SHORT).show()
        }

    }
}