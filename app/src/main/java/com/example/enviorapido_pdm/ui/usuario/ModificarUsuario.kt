package com.example.enviorapido_pdm.ui.usuario

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R
import com.example.enviorapido_pdm.Usuarios

class ModificarUsuario : AppCompatActivity() {

    //variable para la conexion a la bd
    private lateinit var dbHelper: ConexionDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_usuario)

        dbHelper= ConexionDataBaseHelper(this)
        val btnBuscar:Button=findViewById(R.id.btnBuscar)
        btnBuscar.setOnClickListener()
        {
            BuscarUsuario()
        }
        val btnActualizar:Button=findViewById(R.id.btnActualizar)
        btnActualizar.setOnClickListener()
        {
            ActualizarUsuario()
        }
        val btnEliminar:Button=findViewById(R.id.btnEliminar)
        btnEliminar.setOnClickListener()
        {
            val email_persona:EditText=findViewById(R.id.txtCorreo_persona)
            if (email_persona.getText().toString().isEmpty())
            {
                Toast.makeText(this, "El email esta vacio",Toast.LENGTH_SHORT).show()
            }
            else
            {
                ConfirmacionDialogoEliminar()
            }
        }
    }

    fun BuscarUsuario()
    {
        val email_persona:EditText=findViewById(R.id.txtCorreo_persona)
        val NombreRecuperado:EditText=findViewById(R.id.txtNombreUsuario)
        val ApellidoRecuperado:EditText=findViewById(R.id.txtApellidoUsuario)
        val UsuarioRecuperado:EditText=findViewById(R.id.txtUsuario)
        val ContraseñadRecuperada:EditText=findViewById(R.id.txtcontrasenaUsuario)
        if (email_persona.getText().toString().isEmpty())
        {
            Toast.makeText(this,"El correo esta vacio", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val UsuarioBuscado:ArrayList<Usuarios> =dbHelper.RecuperarUsuario(email_persona.getText().toString().uppercase())
            if(UsuarioBuscado.size==0)
            {
                Toast.makeText(this,"Usuario no existe", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this,"Encontrado", Toast.LENGTH_SHORT).show()
                email_persona.setText(UsuarioBuscado[0].email_persona)
                NombreRecuperado.setText(UsuarioBuscado[0].primer_nombre_persona)
                ApellidoRecuperado.setText(UsuarioBuscado[0].primer_apellido_persona)
                UsuarioRecuperado.setText(UsuarioBuscado[0].usuario)
                //ContraseñadRecuperada.setText(UsuarioBuscado[0].contrasena)
            }
        }
    }
    fun ActualizarUsuario()
    {
        val txtCorreo_persona:EditText=findViewById(R.id.txtCorreo_persona)
        val txtNombreUsuario:EditText=findViewById(R.id.txtNombreUsuario)
        val txtApellidoUsuario:EditText=findViewById(R.id.txtApellidoUsuario)
        val txtUsuario:EditText=findViewById(R.id.txtUsuario)
        val txtContrasena:EditText=findViewById(R.id.txtcontrasenaUsuario)
        val IdResultado= dbHelper.ActualizarUsuario(txtCorreo_persona.text.toString(),txtNombreUsuario.text.toString(),txtApellidoUsuario.text.toString(),txtUsuario.text.toString(),null,txtContrasena.text.toString())
        if (IdResultado==0)
        {
            Toast.makeText(this,"Hubo un error, no se pudo actualizar", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"Usuario actualizado con exito", Toast.LENGTH_SHORT).show()
            LimpiarFormulario()
        }
    }
    fun EliminarUsuario()
    {
        val txtCorreo_persona:EditText=findViewById(R.id.txtCorreo_persona)
        val IdResultado=dbHelper.EliminarUsuario(txtCorreo_persona.text.toString())
        if (IdResultado==0)
        {
            Toast.makeText(this,"Hubo un error, no se pudo eliminar", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"Usuario eliminado con exito", Toast.LENGTH_SHORT).show()
            LimpiarFormulario()
        }
    }
    fun LimpiarFormulario()
    {
        val txtCorreo_persona:EditText=findViewById(R.id.txtCorreo_persona)
        val txtNombreUsuario:EditText=findViewById(R.id.txtNombreUsuario)
        val txtApellidoUsuario:EditText=findViewById(R.id.txtApellidoUsuario)
        val txtUsuario:EditText=findViewById(R.id.txtUsuario)
        val txtContrasena:EditText=findViewById(R.id.txtcontrasenaUsuario)
        txtCorreo_persona.setText("")
        txtNombreUsuario.setText("")
        txtApellidoUsuario.setText("")
        txtUsuario.setText("")
        txtContrasena.setText("")
    }
    fun ConfirmacionDialogoEliminar() {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Usuario")
            .setMessage("Esta seguro de eliminar el Usuario?")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    EliminarUsuario()
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                })
            .show()
            .setCancelable(false)
    }


}