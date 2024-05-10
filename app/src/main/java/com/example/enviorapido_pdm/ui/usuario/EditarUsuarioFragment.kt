package com.example.enviorapido_pdm.ui.usuario

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.enviorapido_pdm.R

class EditarUsuarioFragment : Fragment() {


    companion object {
        fun newInstance() = EditarUsuarioFragment()
    }

    private lateinit var viewModel: EditarUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_editar_usuario, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditarUsuarioViewModel::class.java)
        // TODO: Use the ViewModel
        // Ocultar el botón en el fragmento
        val button: Button? = activity?.findViewById(R.id.button2)
        button?.visibility = View.GONE
    }
    

}