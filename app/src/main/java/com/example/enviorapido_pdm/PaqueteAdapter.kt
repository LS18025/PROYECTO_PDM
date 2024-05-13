package com.example.enviorapido_pdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PaqueteAdapter(private val listaPaquete: ArrayList<Paquete>) : RecyclerView.Adapter<PaqueteAdapter.PaqueteViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(idPaquete: Int)
    }
    class PaqueteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idPaquete: TextView = itemView.findViewById(R.id.textIdPaquete)
        val idEnvio: TextView = itemView.findViewById(R.id.textIdEnvio)
        val costoPaquete: TextView = itemView.findViewById(R.id.textCosto)
        val pesoPaquete: TextView = itemView.findViewById(R.id.textPeso)
        val tamanoPaquete: TextView = itemView.findViewById(R.id.textTamano)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaqueteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_paquete, parent, false)
        return PaqueteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PaqueteViewHolder, position: Int) {
        val currentItem = listaPaquete[position]

        holder.idPaquete.text = "ID Paquete: ${currentItem.idPaquete}"
        holder.idEnvio.text = "ID Envío: ${currentItem.idEnvio}"
        holder.costoPaquete.text = "Costo: ${currentItem.costoPaquete}"
        holder.pesoPaquete.text = "Peso: ${currentItem.pesoPaquete}"
        holder.tamanoPaquete.text = "Tamaño: ${currentItem.tamanoPaquete}"
    }

    override fun getItemCount(): Int {
        return listaPaquete.size
    }
}