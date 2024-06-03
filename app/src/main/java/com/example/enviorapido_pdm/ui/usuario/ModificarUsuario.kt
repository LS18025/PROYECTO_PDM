package com.example.enviorapido_pdm.ui.usuario

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.enviorapido_pdm.ui.BaseDatos.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R

class ModificarUsuario : AppCompatActivity() {

    //variable para la conexion a la bd
    private lateinit var dbHelper: ConexionDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_usuario)

        dbHelper= ConexionDataBaseHelper(this)

        //variable para recibir el correo
        val correoRecibido = intent.getStringExtra("correo")?: ""
        Toast.makeText(this, "el correo  es: $correoRecibido", Toast.LENGTH_SHORT).show()

        //Mostrar el correo
        val campoCorreo: TextView = findViewById(R.id.txtEditCorreo_persona)
        MostrarDatosEnCampos(correoRecibido)


       /* val btnBuscar:Button=findViewById(R.id.btnEliminar)
        btnBuscar.setOnClickListener()
        {
            BuscarUsuario()
        }*/
        val btnActualizar:Button=findViewById(R.id.btnActualizar)
        btnActualizar.setOnClickListener()
        {
            ActualizarUsuario()
        }

        //boton eliminar******************

        val btnEliminar:Button=findViewById(R.id.btnEliminar)
        btnEliminar.setOnClickListener()
        {
            val email_persona:EditText=findViewById(R.id.txtEditCorreo_persona)
            if (email_persona.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Usuario esta vacio",Toast.LENGTH_SHORT).show()
            }
            else
            {
                ConfirmacionDialogoEliminar()
            }
        }

    }
    //opciones del menu de items
    override fun onOptionsItemSelected(item:MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.btnUsuarios-> BuscarUsuario()
        }
        return super.onOptionsItemSelected(item)
    }
    fun VerUsuario()
    {
        Toast.makeText(this,"Ver Usuarios", Toast.LENGTH_SHORT).show()
        val intent= Intent(this,ModificarUsuario::class.java)
        startActivity(intent)
    }



    fun BuscarUsuario()
    {
        val email_persona:EditText=findViewById(R.id.txtEditCorreo_persona)
        val NombreRecuperado:EditText=findViewById(R.id.txtEditNombreUsuario)
        val ApellidoRecuperado:EditText=findViewById(R.id.txtEditApellidoUsuario)
        val UsuarioRecuperado:EditText=findViewById(R.id.txtEditUsuario)
        val ContraseñadRecuperada:EditText=findViewById(R.id.txtEditcontrasenaUsuario)
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
        val txtCorreo_persona:EditText=findViewById(R.id.txtEditCorreo_persona)
        val txtNombreUsuario:EditText=findViewById(R.id.txtEditNombreUsuario)
        val txtApellidoUsuario:EditText=findViewById(R.id.txtEditApellidoUsuario)
        val txtUsuario:EditText=findViewById(R.id.txtEditUsuario)
//        val txtContrasena:EditText=findViewById(R.id.txtcontrasenaUsuario)

        val IdResultado= dbHelper.ActualizarUsuario(txtNombreUsuario.text.toString(),txtApellidoUsuario.text.toString(),txtCorreo_persona.text.toString(),txtUsuario.text.toString())
        if (IdResultado==0)
        {
            Toast.makeText(this,"Hubo un error, no se pudo actualizar", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"Usuario actualizado con exito", Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, VerUsuarios::class.java)
            intent.putExtra("userUpdated", true)
            startActivity(intent)



        }
    }
    fun EliminarUsuario()
    {
        val txtCorreo_persona:EditText=findViewById(R.id.txtEditCorreo_persona)
        val IdResultado=dbHelper.EliminarUsuario(txtCorreo_persona.text.toString())

        if (IdResultado==0)
        {
            Toast.makeText(this,"Hubo un error, no se pudo eliminar", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"Usuario eliminado con exito", Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, VerUsuarios::class.java)
            intent.putExtra("userDeleted", true)
            startActivity(intent)
//
        }
    }
    fun LimpiarFormulario()
    {
        val txtCorreo_persona:EditText=findViewById(R.id.txtEditCorreo_persona)
        val txtNombreUsuario:EditText=findViewById(R.id.txtEditNombreUsuario)
        val txtApellidoUsuario:EditText=findViewById(R.id.txtEditApellidoUsuario)
        val txtUsuario:EditText=findViewById(R.id.txtEditUsuario)
        val txtContrasena:EditText=findViewById(R.id.txtEditcontrasenaUsuario)
        txtCorreo_persona.setText("")
        txtNombreUsuario.setText("")
        txtApellidoUsuario.setText("")
        txtUsuario.setText("")
        txtContrasena.setText("")
    }
    fun ConfirmacionDialogoEliminar() {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Usuario")
            .setMessage("¿Esta seguro de eliminar el Usuario?")
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

    fun MostrarDatosEnCampos(correo: String){
        val nombre: EditText = findViewById(R.id.txtEditNombreUsuario)
        val apellido: EditText = findViewById(R.id.txtEditApellidoUsuario)
        val email: EditText = findViewById(R.id.txtEditCorreo_persona)
        //val telefono: EditText = findViewById(R.id.txtTelefonoUsuario)
        val usuario: EditText = findViewById(R.id.txtEditUsuario)
//        val contrsena: EditText = findViewById(R.id.txtcontrasenaUsuario)
//        val confirmarContrasena: EditText = findViewById(R.id.txtRepetirContrasenaUsuario)

        val usuarioLista: ArrayList<Usuarios>
        usuarioLista = dbHelper.RecuperarUsuarioCorreo(correo)
        if(usuarioLista.isNotEmpty()){
            nombre.setText(usuarioLista[0].primer_nombre_persona)
            apellido.setText(usuarioLista[0].primer_apellido_persona)
            email.setText(usuarioLista[0].email_persona)
            usuario.setText(usuarioLista[0].usuario)
        }
    }


}