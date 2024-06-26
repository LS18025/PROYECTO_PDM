package com.example.enviorapido_pdm.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.enviorapido_pdm.ui.envios.HistorialEnvios
import com.example.enviorapido_pdm.databinding.FragmentHomeBinding
import com.example.enviorapido_pdm.ui.envios.RegistrarEnvio
import com.example.enviorapido_pdm.ui.usuario.VerPerfilUsuario
import com.example.enviorapido_pdm.ui.transportista.VistaTransportista
import com.example.enviorapido_pdm.ui.usuario.VerUsuarios

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

        val userRole = requireActivity().intent.getStringExtra("USER_ROLE")

        // Encuentra el ImageButton
        val imageButton4: ImageButton = binding.imageButton4
        val imageButton6: ImageButton = binding.imageButton6
        val imageButton8: ImageButton = binding.imageButton8
        val btnUsuarios: ImageButton = binding.btnUsuarios
        val imageButton: ImageButton = binding.imageButtonTransportista

        //Establecer visibilidad de botones dependiendo del rol del usuario
        if (userRole == "Administrador") {
            btnUsuarios.visibility = View.VISIBLE
            imageButton4.visibility = View.GONE
            imageButton6.visibility = View.GONE
            imageButton8.visibility = View.GONE
            imageButton.visibility = View.VISIBLE
        } else {
            btnUsuarios.visibility = View.GONE
            imageButton.visibility = View.GONE
        }

        btnUsuarios.setOnClickListener {
            val intent = Intent(requireContext(), VerUsuarios::class.java)
            startActivity(intent)
        }

        // Establecer el OnClickListener
        imageButton4.setOnClickListener {
            val intent = Intent(requireContext(), RegistrarEnvio::class.java)
            startActivity(intent)
        }

        // Establecer el OnClickListener
        imageButton6.setOnClickListener {
            // Navega hacia el fragmento RegistrarEnvioFragment
            val intent = Intent(requireContext(), HistorialEnvios::class.java)
            startActivity(intent)
        }


        // Establecer el OnClickListener
        imageButton8.setOnClickListener {
            val intent = Intent(requireContext(), VerPerfilUsuario::class.java)
            startActivity(intent)
        }
        imageButton.setOnClickListener {
            val intent = Intent(requireContext(), VistaTransportista::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
