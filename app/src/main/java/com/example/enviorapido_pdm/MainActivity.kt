package com.example.enviorapido_pdm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.enviorapido_pdm.databinding.ActivityMainBinding
import com.example.enviorapido_pdm.ui.administracion.LoginActivity
import com.example.enviorapido_pdm.ui.chat.ChatFragment
import com.example.enviorapido_pdm.ui.departamentos.VistaInsertarDepartamento
import com.example.enviorapido_pdm.ui.notifications.NotificationsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Obtener referencia al botón
        //val button: Button = findViewById(R.id.button2)

        // Agregar OnClickListener al botón
        //button.setOnClickListener {
            // Navegar al fragmento RegistrarEnvioFragment
            //navController.navigate(R.id.envioExitosoFragment)
        //}

        //val btnDepa:Button = findViewById(R.id.btnDepa)

        //btnDepa.setOnClickListener()
        //{
          //  val Intent = Intent(this,VistaPaquete::class.java)
           // startActivity(Intent)
        //}




        firebaseAuth = Firebase.auth

        //
        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId)
            {
                R.id.navigation_home -> {

                    navController.navigate(R.id.navigation_home)
                    true
                }

                R.id.fragment_chat -> {

                    navController.navigate(R.id.fragment_chat)
                    true
                }

                R.id.navigation_notifications -> {

                    navController.navigate(R.id.navigation_notifications)
                    true
                }

                R.id.logoutFragment ->
                    {
                        CerrarSesion()
                        true
                    }
                else -> false


            }
        }

    }
    private fun CerrarSesion ()
    {
        firebaseAuth.signOut()
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }
}