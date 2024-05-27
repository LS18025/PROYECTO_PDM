package com.example.enviorapido_pdm.ui.paquete

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.enviorapido_pdm.R

class PaqueteAdapter(
    public val listaPaquete: ArrayList<Paquete>,
    private val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<PaqueteAdapter.PaqueteViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    interface OnItemSelectedListener {
        fun onItemSelected(paquete: Paquete)
    }
    inner class PaqueteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idPaquete: TextView = itemView.findViewById(R.id.textIdPaquete)
        val idEnvio: TextView = itemView.findViewById(R.id.textIdEnvio)
        val costoPaquete: TextView = itemView.findViewById(R.id.textCosto)
        val pesoPaquete: TextView = itemView.findViewById(R.id.textPeso)
        val tamanoPaquete: TextView = itemView.findViewById(R.id.textTamano)

        init {
            itemView.setOnClickListener {
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                onItemSelectedListener.onItemSelected(listaPaquete[adapterPosition])
            }
        }
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

        holder.itemView.isSelected = (selectedPosition == position)
    }

    override fun getItemCount(): Int {
        return listaPaquete.size
    }




}