package com.example.enviorapido_pdm

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EnviosAdapter(private val context: Context, private val enviosList: ArrayList<Envios>) :
    RecyclerView.Adapter<EnviosAdapter.EnviosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnviosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_envio, parent, false)
        return EnviosViewHolder(view)
    }

    override fun onBindViewHolder(holder: EnviosViewHolder, position: Int) {
        val envio = enviosList[position]
        holder.textViewEnvioDireccion.text = envio.direccion
        holder.textViewEnvioFecha.text = envio.fechaEnvio

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetalleEnvio::class.java)
            intent.putExtra("ENVIO_ID", envio.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return enviosList.size
    }

    class EnviosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEnvioDireccion: TextView = itemView.findViewById(R.id.textViewEnvioDireccion)
        val textViewEnvioFecha: TextView = itemView.findViewById(R.id.textViewEnvioFecha)
    }
}
