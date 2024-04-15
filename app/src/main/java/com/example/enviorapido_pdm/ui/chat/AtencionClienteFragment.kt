package com.example.enviorapido_pdm.ui.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.enviorapido_pdm.R

class AtencionClienteFragment : Fragment() {

    companion object {
        fun newInstance() = AtencionClienteFragment()
    }

    private lateinit var viewModel: AtencionClienteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_atencion_cliente, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AtencionClienteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}