package com.example.enviorapido_pdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/*Sirve para poblar toda la información en el RecyclerView
Esta clase se modificara para usar los datos de la base de de datos posteriormente*/
class CustomAdapter(nombreRecuperados:ArrayList<String>,apellidoRecuperado:ArrayList<String>,emailRecuperado:ArrayList<String>,telefonoRecuperado:ArrayList<String>,usuarioRecuperado:ArrayList<String>,rolRecuperado:ArrayList<String>):RecyclerView.Adapter<CustomAdapter.ViewHolder>()
    {

        private lateinit var NombreRecuperados:ArrayList<String>
        private lateinit var ApellidoRecuperado:ArrayList<String>
        private lateinit var EmailRecuperado:ArrayList<String>
        private lateinit var TelefonoRecuperado:ArrayList<String>
        private lateinit var UsuarioRecuperado:ArrayList<String>
        private lateinit var RolRecuperado:ArrayList<String>

        init
        {
            this.NombreRecuperados=nombreRecuperados
            this.ApellidoRecuperado=apellidoRecuperado
            this.EmailRecuperado=emailRecuperado
            this.TelefonoRecuperado=telefonoRecuperado
            this.UsuarioRecuperado=usuarioRecuperado
            this.RolRecuperado=rolRecuperado
        }

        public class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
        {
            var itemNombreUser: TextView=itemView.findViewById(R.id.NombreUser)
            var itemApellidoUser: TextView=itemView.findViewById(R.id.ApellidoUser)
            var itemEmailUser: TextView=itemView.findViewById(R.id.EmailUser)
            var itemTelefonoUser: TextView=itemView.findViewById(R.id.TelefonolUser)
            var itemUsuarioUser: TextView=itemView.findViewById(R.id.UsuarioUser)
            var itemRolUser: TextView=itemView.findViewById(R.id.RolUser)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v=LayoutInflater.from(parent.context).inflate(R.layout.todos_los_usuarios,parent,false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return NombreRecuperados.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemNombreUser.text=NombreRecuperados[position]
            holder.itemApellidoUser.text=ApellidoRecuperado[position]
            holder.itemEmailUser.text=EmailRecuperado[position]
            holder.itemTelefonoUser.text=TelefonoRecuperado[position]
            holder.itemUsuarioUser.text=UsuarioRecuperado[position]
            holder.itemRolUser.text=RolRecuperado[position]
        }
}