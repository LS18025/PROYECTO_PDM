package com.example.enviorapido_pdm.ui.envios

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.enviorapido_pdm.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RegistrarEnvioFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrarEnvioFragment()
    }

    private lateinit var viewModel: RegistrarEnvioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_registrar_envio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el listener para el ImageButton
        view.findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            // Navegar al fragmento EnvioExitosoFragment
            findNavController().navigate(R.id.envioExitosoFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrarEnvioViewModel::class.java)
        // TODO: Use the ViewModel
        // Ocultar la barra de navegación inferior al navegar al fragmento RegistrarEnvioFragment
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navView?.visibility = View.GONE
        // Ocultar el botón en el fragmento
        val button: Button? = activity?.findViewById(R.id.button2)
        button?.visibility = View.GONE

    }

}