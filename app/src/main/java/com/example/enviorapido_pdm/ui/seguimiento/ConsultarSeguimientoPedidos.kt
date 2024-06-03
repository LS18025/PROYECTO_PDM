package com.example.enviorapido_pdm.ui.seguimiento

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.enviorapido_pdm.ui.BaseDatos.ConexionDataBaseHelper
import com.example.enviorapido_pdm.R

class ConsultarSeguimientoPedidos : AppCompatActivity() {

    private lateinit var txtCodPedido: EditText
    private lateinit var listView: ListView
    private lateinit var seguimientoAdapter: SeguimientoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_seguimiento_pedidos)

        // Inicializar vistas
        txtCodPedido = findViewById(R.id.txtCodPedido)
        listView = findViewById(R.id.listViewPedidos)

        // Inicializar el adaptador
        seguimientoAdapter = SeguimientoAdapter(this, ArrayList())
        listView.adapter = seguimientoAdapter // Asignar adaptador a la ListView

        // Manejar el evento de clic del botón de consulta
        val btnConsultar: Button = findViewById(R.id.buttonConsultar)
        btnConsultar.setOnClickListener {
            val idEnvio = txtCodPedido.text.toString().toIntOrNull()

            if (idEnvio != null) {
                val seguimientos = ConexionDataBaseHelper(this).recuperarSeguimientoPorIdEnvio(idEnvio)
                if (seguimientos.isEmpty()) {
                    Toast.makeText(this, "No se encontraron seguimientos para el pedido", Toast.LENGTH_SHORT).show()
                } else {
                    seguimientoAdapter.clear()
                    seguimientoAdapter.addAll(seguimientos)
                }
            } else {
                Toast.makeText(this, "Ingrese un código de pedido válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
