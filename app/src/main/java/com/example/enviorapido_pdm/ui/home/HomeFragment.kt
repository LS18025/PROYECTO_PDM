package com.example.enviorapido_pdm.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.enviorapido_pdm.databinding.FragmentHomeBinding
import com.example.enviorapido_pdm.R
import com.example.enviorapido_pdm.RegistrarEnvio

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Encuentra el ImageButton
        val imageButton4: ImageButton = binding.imageButton4
        val imageButton6: ImageButton = binding.imageButton6
        val imageButton8: ImageButton = binding.imageButton8


        // Establecer el OnClickListener
        imageButton4.setOnClickListener {
            val intent = Intent(requireContext(), RegistrarEnvio::class.java)
            startActivity(intent)
        }

        // Establecer el OnClickListener
        imageButton6.setOnClickListener {
            // Navega hacia el fragmento RegistrarEnvioFragment
            findNavController().navigate(R.id.historialFragment)
        }

        // Establecer el OnClickListener
        imageButton8.setOnClickListener {
            // Navega hacia el fragmento RegistrarEnvioFragment
            findNavController().navigate(R.id.perfilUsuarioFragment)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
