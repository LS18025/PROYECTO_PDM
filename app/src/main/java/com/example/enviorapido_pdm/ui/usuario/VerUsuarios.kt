package com.example.enviorapido_pdm.ui.usuario

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enviorapido_pdm.ui.BaseDatos.ConexionDataBaseHelper
import com.example.enviorapido_pdm.ui.administracion.CustomAdapter
import com.example.enviorapido_pdm.R

class VerUsuarios : AppCompatActivity(), CustomAdapter.OnUserUpdateListener {

    private lateinit var dbHelper: ConexionDataBaseHelper

    private lateinit var idRecuperado: ArrayList<String>
    private lateinit var nombresRecuperado: ArrayList<String>
    private lateinit var apellidosRecuperado: ArrayList<String>
    private lateinit var emailRecuperado: ArrayList<String>
    private lateinit var telefonoRecuperado: ArrayList<String>
    private lateinit var usuarioRecuperado: ArrayList<String>
    private lateinit var rolRecuperado: ArrayList<String>

    override fun onUserUpdated() {
        llenarDatosEnVista()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuarios)

        if (intent.getBooleanExtra("userUpdated", false)) {
            llenarDatosEnVista()
        }else if (intent.getBooleanExtra("userDeleted", false)) {
            llenarDatosEnVista()
        }

        dbHelper = ConexionDataBaseHelper(this)

        idRecuperado = ArrayList()
        nombresRecuperado = ArrayList()
        apellidosRecuperado = ArrayList()
        emailRecuperado = ArrayList()
        telefonoRecuperado = ArrayList()
        usuarioRecuperado = ArrayList()
        rolRecuperado = ArrayList()


        llenarDatosEnVista()
    }

//   override fun onItemClick(position: Int) {
////        val intent = Intent(this, ModificarUsuario::class.java)
////        intent.putExtra("id_usuario", idRecuperado[position])
////        startActivity(intent)
//   }

    private fun llenarDatosEnVista() {
        val verUsuarios: ArrayList<Usuarios> = dbHelper.RecuperarTodosLosUsuarios()
        Toast.makeText(this, "cantidad de usuarios: ${verUsuarios.size}", Toast.LENGTH_SHORT).show()
        if (verUsuarios.isEmpty()) {
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show()
        } else {
            for (user in verUsuarios) {
                idRecuperado.add(user.id_usuario)
                nombresRecuperado.add(user.primer_nombre_persona)
                apellidosRecuperado.add(user.primer_apellido_persona)
                emailRecuperado.add(user.email_persona)
                telefonoRecuperado.add(user.telefono)
                usuarioRecuperado.add(user.usuario)
                rolRecuperado.add(user.rol)
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = CustomAdapter(
            idRecuperado,
            nombresRecuperado,
            apellidosRecuperado,
            emailRecuperado,
            telefonoRecuperado,
            usuarioRecuperado,
            rolRecuperado,
           this
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}