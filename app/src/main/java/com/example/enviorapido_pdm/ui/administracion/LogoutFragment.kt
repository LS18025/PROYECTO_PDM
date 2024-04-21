package com.example.enviorapido_pdm.ui.administracion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.enviorapido_pdm.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class LogoutFragment : Fragment() {

    companion object {
        fun newInstance() = LogoutFragment()
    }

    private lateinit var viewModel: LogoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogoutViewModel::class.java)
        // TODO: Use the ViewModel
        // Ocultar la barra de navegación inferior al navegar al fragmento RegistrarEnvioFragment
        val navView: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navView?.visibility = View.GONE
        // Ocultar el botón en el fragmento
        val button: Button? = activity?.findViewById(R.id.button2)
        button?.visibility = View.GONE
    }

}