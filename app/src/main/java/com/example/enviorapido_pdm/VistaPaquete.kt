    package com.example.enviorapido_pdm

    import android.app.Activity
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import android.widget.Button
    import android.widget.ImageButton
    import android.widget.Toast
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.google.android.material.dialog.MaterialAlertDialogBuilder

    class VistaPaquete : AppCompatActivity(), PaqueteAdapter.OnItemSelectedListener {

        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: PaqueteAdapter
        private lateinit var dbHelper: ConexionDataBaseHelper

        private var selectedPaquete: Paquete? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_vista_paquete)

            recyclerView = findViewById(R.id.listaPaquete)
            recyclerView.layoutManager = LinearLayoutManager(this)

            adapter = PaqueteAdapter(ConexionDataBaseHelper(this).RecuperarTodoslosPaquetes(), this)
            recyclerView.adapter = adapter



            val btnAgregar: ImageButton = findViewById(R.id.btnAgregarPaquete)
            val btnEditar: ImageButton = findViewById(R.id.btnEditarPaquete)
            val btnEliminar: ImageButton = findViewById(R.id.btnBorrarPaquete)

            btnAgregar.setOnClickListener {
                val intent = Intent(this, AgregarPaquete::class.java)
                startActivity(intent)
            }

            btnEditar.setOnClickListener {
                selectedPaquete?.let {
                    val intent = Intent(this, EditarPaquete::class.java)
                    intent.putExtra("id_paquete", it.idPaquete)
                    startActivity(intent)
                }
            }
            btnEliminar.setOnClickListener {
                if (selectedPaquete != null) {
                    dbHelper = ConexionDataBaseHelper(this)
                    val idPaquete = selectedPaquete!!.idPaquete
                    val filasAfectadas = dbHelper.EliminarPaquete(idPaquete)
                    if (filasAfectadas > 0) {
                        // Eliminación exitosa, actualiza la lista de paquetes
                        adapter.listaPaquete.remove(selectedPaquete!!)
                        adapter.notifyDataSetChanged()
                        selectedPaquete = null // Limpia la selección
                    } else {
                        // Error al eliminar, muestra un mensaje de error
                        Toast.makeText(this, "Error al eliminar el paquete", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // No hay paquete seleccionado, muestra un mensaje de advertencia
                    Toast.makeText(this, "Selecciona un paquete para eliminar", Toast.LENGTH_SHORT).show()
                }
            }


        }

        override fun onItemSelected(paquete: Paquete) {
            selectedPaquete = paquete
        }


    }