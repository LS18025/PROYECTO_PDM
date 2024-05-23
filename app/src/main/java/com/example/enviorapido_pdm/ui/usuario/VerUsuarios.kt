package com.example.enviorapido_pdm.ui.usuario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.CustomAdapter
import com.example.enviorapido_pdm.R
import com.example.enviorapido_pdm.Usuarios

class VerUsuarios : AppCompatActivity() {

    //VARIABLES PARA LA CONEXION CON EL MODELO DE CONEXION A LA BD
    private lateinit var dbHelper:ConexionDataBaseHelper

    //Arreglos que serviran para poblar el RecyclerView
    private lateinit var IdRecuperado:ArrayList<String>
    private lateinit var NombresRecuperado:ArrayList<String>
    private lateinit var ApellidosRecuperado:ArrayList<String>
    private lateinit var EmailRecuperado:ArrayList<String>
    private lateinit var TelefonoRecuperado:ArrayList<String>
    private lateinit var UsuarioRecuperado:ArrayList<String>
    private lateinit var RolRecuperado:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuarios)

        dbHelper=ConexionDataBaseHelper(this)


        IdRecuperado= ArrayList()
        NombresRecuperado= ArrayList()
        ApellidosRecuperado=ArrayList()
        EmailRecuperado=ArrayList()
        TelefonoRecuperado=ArrayList()
        UsuarioRecuperado=ArrayList()
        RolRecuperado=ArrayList()

        LlenarDatosEnVista()
    }

    fun AgregarUsuario()
    {
        Toast.makeText(this,"Agregar Usuario", Toast.LENGTH_SHORT).show()
        val intent= Intent(this,ConexionDataBaseHelper::class.java)
        startActivity(intent)
    }

    fun ModificarUsuario()
    {
        Toast.makeText(this,"Modificar Usuario", Toast.LENGTH_SHORT).show()
        val intent= Intent(this,ModificarUsuario::class.java)
        startActivity(intent)
    }

    fun LlenarDatosEnVista()
    {

        val VerUsuarios: ArrayList<Usuarios> =dbHelper.RecuperarTodosLosUsuarios()

        if (VerUsuarios.size==0)
        {
            Toast.makeText(this,"No hay datos", Toast.LENGTH_SHORT).show()
        }
        else
        {
            for (user in VerUsuarios) {
                // Accede a los atributos o métodos de cada objeto "Usuario"
                IdRecuperado.add(user.id_usuario)
                NombresRecuperado.add(user.primer_nombre_persona)
                ApellidosRecuperado.add(user.primer_apellido_persona)
                EmailRecuperado.add(user.email_persona)
                TelefonoRecuperado.add(user.telefono)
                UsuarioRecuperado.add(user.usuario)
                RolRecuperado.add(user.rol)
            }
        }
        val recyclerView: RecyclerView =findViewById(R.id.recyclerView)
        val adapter= CustomAdapter(IdRecuperado,NombresRecuperado,ApellidosRecuperado,EmailRecuperado,TelefonoRecuperado,UsuarioRecuperado,RolRecuperado)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter=adapter
        //recyclerView.setAdapter(adapter) // esta linea hace lo mismo es otra forma de asignar el custom adapter
    }
}