package com.example.enviorapido_pdm.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.enviorapido_pdm.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AdaptadorChat(contexto : Context, chatLista : List<ChatModelo>,imagenurl : String)
    : RecyclerView.Adapter<AdaptadorChat.ViewHolder?>() {

        private val contexto : Context
        private val chatLista : List<ChatModelo>
        private val imagenurl : String
        var firebaseUser : FirebaseUser= FirebaseAuth.getInstance().currentUser!!

    init {
        this.contexto = contexto
        this.chatLista = chatLista
        this.imagenurl= imagenurl

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        /*vista de item mnesaje  izquierda*/
        var imagen_perfil_mensaje : ImageView?=null
        var TXT_ver_mensaje : TextView?=null
        var imagen_enviada_izquierdo : ImageView?=null
        var TXT_visto : TextView?=null

        /*vista de item mnesaje  derecha*/

        var imagen_enviada_derecha : ImageView?=null

        init {
            imagen_perfil_mensaje = itemView.findViewById(R.id.imagen_perfil_mensaje)
            TXT_ver_mensaje = itemView.findViewById(R.id.TXT_ver_mensaje)
            imagen_enviada_izquierdo = itemView.findViewById(R.id.imagen_enviada_izquierdo)
            TXT_visto = itemView.findViewById(R.id.TXT_visto)
            imagen_enviada_derecha = itemView.findViewById(R.id.imagen_enviada_derecha)



        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return  if (position==1){
            val view : View = LayoutInflater.from(contexto).inflate(com.example.enviorapido_pdm.R.layout.item_mensaje_derecho, parent,false)
            ViewHolder(view)
        }else{
            val view : View = LayoutInflater.from(contexto).inflate(com.example.enviorapido_pdm.R.layout.item_mensaje_izquierdo, parent, false)
            ViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return chatLista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

      val chat : ChatModelo = chatLista[position]
        Glide.with(contexto).load(imagenurl).placeholder(R.drawable.ic_imagen_chat).into(holder.imagen_perfil_mensaje!!)

       /*si el mensaje contiene una imagen*/
        if (chat.getMensaje().equals("Se ha enviado la imagen") && !chat.getUrl().equals("")){

            /*condicion para mostra si el usuario envia una imagen como mensaje*/

            if (chat.getEmisor().equals(firebaseUser!!.uid)){

                holder.TXT_ver_mensaje!!.visibility = View.GONE
                holder.imagen_enviada_derecha!!.visibility = View.VISIBLE
                Glide.with(contexto).load(chat.getUrl()).placeholder(R.drawable.ic_imagen_enviada).into(holder.imagen_enviada_derecha!!)

            }

            /*condicion para mostra si el usuario envia una imagen como mensaje */
            else if (!chat.getEmisor().equals(firebaseUser!!.uid)){

                holder.TXT_ver_mensaje!!.visibility = View.GONE
                holder.imagen_enviada_izquierdo!!.visibility = View.VISIBLE
                Glide.with(contexto).load(chat.getUrl()).placeholder(R.drawable.ic_imagen_enviada).into(holder.imagen_enviada_izquierdo!!)


            }


        }

        /*si el mensaje contiene solo texto*/
        else{

            holder.TXT_ver_mensaje!!.text = chat.getMensaje()


        }












    }

    override fun getItemViewType(position: Int): Int {
        return if (chatLista[position].getEmisor().equals(firebaseUser!!.uid)){
            1
        }
        else{
            0
        }
    }
}