package com.example.enviorapido_pdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val idRecuperados: ArrayList<String>,
    private val nombreRecuperados: ArrayList<String>,
    private val apellidoRecuperado: ArrayList<String>,
    private val emailRecuperado: ArrayList<String>,
    private val telefonoRecuperado: ArrayList<String>,
    private val usuarioRecuperado: ArrayList<String>,
    private val rolRecuperado: ArrayList<String>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val itemNombreUser: TextView = itemView.findViewById(R.id.NombreUser)
        val itemApellidoUser: TextView = itemView.findViewById(R.id.ApellidoUser)
        val itemEmailUser: TextView = itemView.findViewById(R.id.EmailUser)
        val itemTelefonoUser: TextView = itemView.findViewById(R.id.TelefonolUser)
        val itemUsuarioUser: TextView = itemView.findViewById(R.id.UsuarioUser)
        val itemRolUser: TextView = itemView.findViewById(R.id.RolUser)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todos_los_usuarios, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return nombreRecuperados.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNombreUser.text = nombreRecuperados[position]
        holder.itemApellidoUser.text = apellidoRecuperado[position]
        holder.itemEmailUser.text = emailRecuperado[position]
        holder.itemTelefonoUser.text = telefonoRecuperado[position]
        holder.itemUsuarioUser.text = usuarioRecuperado[position]
        holder.itemRolUser.text = rolRecuperado[position]
    }
}
