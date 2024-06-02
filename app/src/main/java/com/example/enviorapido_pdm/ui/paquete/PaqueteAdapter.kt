package com.example.enviorapido_pdm.ui.paquete

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.enviorapido_pdm.R

class PaqueteAdapter(
    public val listaPaquete: ArrayList<Paquete>,
    private val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    inner class EmptyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_EMPTY) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_empty_list, parent, false)
            EmptyListViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_paquete, parent, false)
            PaqueteViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PaqueteViewHolder) {
            val currentItem = listaPaquete[position]
            holder.idPaquete.text = "ID Paquete: ${currentItem.idPaquete}"
            holder.idEnvio.text = "ID Envío: ${currentItem.idEnvio}"
            holder.costoPaquete.text = "Costo: $${currentItem.costoPaquete}"
            holder.pesoPaquete.text = "Peso: ${currentItem.pesoPaquete} lb"
            holder.tamanoPaquete.text = "Tamaño: ${currentItem.tamanoPaquete}"
            holder.itemView.isSelected = (selectedPosition == position)
        }
    }

    override fun getItemCount(): Int {
        return if (listaPaquete.isEmpty()) {
            1 // Si la lista está vacía, devolvemos 1 para mostrar el elemento de lista vacía
        } else {
            listaPaquete.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listaPaquete.isEmpty()) {
            VIEW_TYPE_EMPTY // Si la lista está vacía, devolvemos el tipo de vista del elemento de lista vacía
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }

    fun actualizarLista(nuevaListaPaquete: ArrayList<Paquete>) {
        listaPaquete.clear()
        listaPaquete.addAll(nuevaListaPaquete)
        notifyDataSetChanged()
    }
}
