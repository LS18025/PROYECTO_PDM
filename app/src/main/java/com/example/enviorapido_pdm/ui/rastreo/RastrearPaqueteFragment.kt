package com.example.enviorapido_pdm.ui.rastreo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.enviorapido_pdm.R

class RastrearPaqueteFragment : Fragment() {

    companion object {
        fun newInstance() = RastrearPaqueteFragment()
    }

    private lateinit var viewModel: RastrearPaqueteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rastrear_paquete, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RastrearPaqueteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}