package com.example.enviorapido_pdm


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransportistaAdapter(
    private val listaTransportistas: ArrayList<Transportista>,
) : RecyclerView.Adapter<TransportistaAdapter.TransportistaViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(idTransportista: Int)
    }

    class TransportistaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTransportista: TextView = itemView.findViewById(R.id.textIdTransportista)
        val nombreTransportista: TextView = itemView.findViewById(R.id.textNombreTransportista)
        val apellidoTransportista: TextView = itemView.findViewById(R.id.textApellidoTransportista)
        val telefonoTransportista: TextView = itemView.findViewById(R.id.textTelefonoTransportista)
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

        holder.itemView.setOnClickListener {
            val idTransportista = currentItem.idTransportista
            val context = holder.itemView.context
            if (context is VistaTransportista) {
                context.onItemClick(idTransportista)
            }
        }
    }

    override fun getItemCount(): Int {
        return listaTransportistas.size
    }
}
