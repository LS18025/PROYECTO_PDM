package com.example.enviorapido_pdm.ui.administracion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.enviorapido_pdm.R

class RegistrarUsuarioFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrarUsuarioFragment()
    }

    private lateinit var viewModel: RegistrarUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registrar_usuario, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrarUsuarioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}