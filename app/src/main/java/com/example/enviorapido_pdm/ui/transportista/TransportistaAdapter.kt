package com.example.enviorapido_pdm.ui.transportista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.enviorapido_pdm.R

class TransportistaAdapter(
    var listaTransportistas: ArrayList<Transportista>,
    private val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<TransportistaAdapter.TransportistaViewHolder>() {
    private var selectedPosition = RecyclerView.NO_POSITION

    interface OnItemSelectedListener {
        fun onItemSelected(transportista: Transportista)
    }

    inner class TransportistaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTransportista: TextView = itemView.findViewById(R.id.textIdTransportista)
        val nombreTransportista: TextView = itemView.findViewById(R.id.textNombreTransportista)
        val apellidoTransportista: TextView = itemView.findViewById(R.id.textApellidoTransportista)
        val telefonoTransportista: TextView = itemView.findViewById(R.id.textTelefonoTransportista)

        init {
            itemView.setOnClickListener {
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                onItemSelectedListener.onItemSelected(listaTransportistas[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportistaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_transportista, parent, false)
        return TransportistaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransportistaViewHolder, position: Int) {
        val currentItem = listaTransportistas[position]

        holder.idTransportista.text = "ID: ${currentItem.idTransportista}"
        holder.nombreTransportista.text = "Nombre: ${currentItem.nombreTransportista}"
        holder.apellidoTransportista.text = "Apellido: ${currentItem.apellidoTransportista}"
        holder.telefonoTransportista.text = "Teléfono: ${currentItem.telefonoTransportista}"

        holder.itemView.isSelected = (selectedPosition == position)
    }

    override fun getItemCount(): Int {
        return listaTransportistas.size
    }
}
