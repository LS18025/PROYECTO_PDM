package com.example.enviorapido_pdm.ui.administracion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.enviorapido_pdm.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegistrarCuenta : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var reference : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cuenta)

        val txtNombreUsuario : TextView = findViewById(R.id.txtNombre)
        val txtApellidoUsuario : TextView = findViewById(R.id.txtApellido)
        val txtEmail : TextView = findViewById(R.id.editTextTextEmailAddress)
        val usuario : TextView = findViewById(R.id.txtUsuario)
        val contraseña : TextView = findViewById(R.id.txtContraseña)
        val contraseña2 : TextView = findViewById(R.id.repetirContrasena)
        val btnRegistrarUsuario : ImageButton = findViewById(R.id.btnRegistrarUsuario)

        btnRegistrarUsuario.setOnClickListener()
        {
            var txtcontraseña = contraseña.text.toString()
            var txtcontraseña2 = contraseña2.text.toString()

            if (txtcontraseña.equals(txtcontraseña2))
            {
                crearCuenta(txtEmail.text.toString(),contraseña.text.toString())
            }
            else
            {
                Toast.makeText(baseContext,"Error: Las contraseñas no coinciden",Toast.LENGTH_SHORT).show()
                contraseña.requestFocus()
            }
        }

        firebaseAuth = Firebase.auth

        val btnRegresar: ImageButton = findViewById(R.id.btnCancelarRegistro)
        btnRegresar.setOnClickListener()
        {
            val i = Intent (this, LoginActivity::class.java)
            startActivity(i)
        }
    }

    private fun crearCuenta(email:String,password:String)
    {
        val txtNombreUsuario : TextView = findViewById(R.id.txtNombre)
        val txtApellidoUsuario : TextView = findViewById(R.id.txtApellido)
        val usuario : TextView = findViewById(R.id.txtUsuario)

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful)
                {
                    ConfirmarEmail()

                    val firebaseUser = firebaseAuth.currentUser
                    val uid = firebaseUser?.uid ?: ""
                    // Toast.makeText(baseContext,"Usuario registrado satisfactoriamente, se necesita verificacion",Toast.LENGTH_SHORT).show()

                    /*Guardar datos local*/
                    val conexionDB = ConexionDataBaseHelper(this)
                    conexionDB.AgregarUsuario(uid,2,txtNombreUsuario.text.toString(),txtApellidoUsuario.text.toString(),email,"Telefono",usuario.text.toString(),"Contrasena")

                    val i = Intent (this, LoginActivity::class.java)
                    startActivity(i)



                    /*Guardar datos en Firebase*/
                    reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)

                    val hashMap = HashMap<String, Any>()
                    val h_nombre_usuario : String = usuario.text.toString()
                    val h_email : String = email


                    hashMap["uid"] = uid
                    hashMap["n_usuario"] = h_nombre_usuario
                    hashMap["email"] = h_email
                    hashMap["imagen"] = ""
                    hashMap["buscar"]= h_nombre_usuario.lowercase()

                    /*nuevos campos*/

                    hashMap["nombres"] = txtNombreUsuario.text.toString()
                    hashMap["apellidos"] = txtApellidoUsuario.text.toString()
                    hashMap["estado"] = "ofline"


                    reference.updateChildren(hashMap).addOnCompleteListener{task2->

                        if (task2.isSuccessful){

                            val intent = Intent(this@RegistrarCuenta,LoginActivity::class.java)
                            Toast.makeText(applicationContext,"Usuario registrado satisfactoriamente, se necesita verificacion",Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }


                    }
                        .addOnFailureListener{e->
                        Toast.makeText(applicationContext,"{${e.message}}",Toast.LENGTH_SHORT).show()
                    }


                }
                else
                {
                    Toast.makeText(baseContext,"Algo salió mal, Error: "+task.exception,Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener{e->
                Toast.makeText(applicationContext,"{${e.message}}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun ConfirmarEmail()
    {
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this) { task ->
            if (task.isSuccessful)
            {

            }
            else
            {

            }
        }
    }
}