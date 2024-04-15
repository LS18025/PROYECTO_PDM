package com.example.enviorapido_pdm.ui.envios

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.enviorapido_pdm.R

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrarEnvioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}