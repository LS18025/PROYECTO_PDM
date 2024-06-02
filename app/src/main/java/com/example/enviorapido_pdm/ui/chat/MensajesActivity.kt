package com.example.enviorapido_pdm.ui.chat

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.enviorapido_pdm.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.net.URI

class MensajesActivity : AppCompatActivity() {

    private lateinit var N_usuario : TextView
    private lateinit var imagen_perfil : ImageView
    private lateinit var Et_mensaje : EditText
    private lateinit var  IB_Enviar : ImageButton
    private lateinit var IB_Adjuntar : ImageButton

    var uid_usuario_seleccionado : String = ""
    var FirebaseUser : FirebaseUser ?=null

    private var imagen2Uri : Uri?=null

    lateinit var RV_chats : RecyclerView
    var chatadapter : AdaptadorChat?=null
    var chatList : List<ChatModelo>?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mensajes)
        InicializarVistas()
        ObtenerUid()
        ObtenerUsuarioSeleccionado()

        IB_Adjuntar.setOnClickListener{

            AbrirGaleria()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        IB_Enviar.setOnClickListener(){
            val mensaje = Et_mensaje.text.toString()
            if (mensaje.isEmpty()){
                Toast.makeText(applicationContext, "Porfavor Ingrese un Mensaje", Toast.LENGTH_SHORT).show()
            }else{
                EnviaMensaje(FirebaseUser!!.uid, uid_usuario_seleccionado,mensaje)
                Et_mensaje.setText("")
            }
        }
    }



    private fun ObtenerUid (){

        intent = intent
        uid_usuario_seleccionado=intent.getStringExtra("uid_usuario").toString()


    }

    private fun EnviaMensaje(uid_emisor: String, uid_receptor: String, mensaje: String) {
        val reference = FirebaseDatabase.getInstance().reference
        val mensajeKey = reference.push().key

        val infomensaje = HashMap<String, Any?>()
        infomensaje["id_mensaje"] = mensajeKey
        infomensaje["emisor"] = uid_emisor
        infomensaje["receptor"] = uid_receptor
        infomensaje["mensaje"] = mensaje
        infomensaje["url"] = ""
        infomensaje["visto"] = false

        reference.child("Chats").child(mensajeKey!!).setValue(infomensaje).addOnCompleteListener {tarea->
            if (tarea.isSuccessful){
                val ListaMensajeEmisor = FirebaseDatabase.getInstance().reference.child("ListaMensajes")
                    .child(FirebaseUser!!.uid)
                    .child(uid_usuario_seleccionado)

                ListaMensajeEmisor.addListenerForSingleValueEvent(object  : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (!snapshot.exists()){
                            ListaMensajeEmisor.child("uid").setValue(uid_usuario_seleccionado)
                        }

                        val  ListaMensajereceptor = FirebaseDatabase.getInstance().reference.child("ListaMensajes")
                            .child(uid_usuario_seleccionado)
                            .child(FirebaseUser!!.uid)
                        ListaMensajereceptor.child("uid").setValue(FirebaseUser!!.uid)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }






    }

    private fun InicializarVistas() {
        N_usuario = findViewById(R.id.N_usuario_chat)
        imagen_perfil = findViewById(R.id.imagen_perfil_chat)
        Et_mensaje = findViewById(R.id.Et_mensaje)
        IB_Adjuntar = findViewById(R.id.IB_Adjuntar)
        IB_Enviar = findViewById(R.id.IB_Enviar)
        FirebaseUser = FirebaseAuth.getInstance().currentUser

        RV_chats = findViewById(R.id.RV_Chats)
        RV_chats.setHasFixedSize(true)
        var linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true

        RV_chats.layoutManager = linearLayoutManager


    }

    private fun ObtenerUsuarioSeleccionado(){

        val reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid_usuario_seleccionado)
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario : Usuario? = snapshot.getValue(Usuario::class.java)
                //obtener nombre de usuario
                N_usuario.text = usuario!!.getN_Usuario()
                //obtener imagen de perfil
                Glide.with(applicationContext).load(usuario.getImagen()).placeholder(R.drawable.ic_item_usuario).into(imagen_perfil)

                RecuperarMensajes(FirebaseUser!!.uid, uid_usuario_seleccionado, usuario.getImagen())

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun RecuperarMensajes(EmisorUid: String, ReceptorUid: String, ReceptorImagen: String?) {

        chatList = ArrayList()
        val reference = FirebaseDatabase.getInstance().reference.child("Chats")
        reference.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (chatList as ArrayList<ChatModelo>).clear()
                for (sn in snapshot.children){
                    val chat =sn.getValue(ChatModelo::class.java)

                    if (chat!!.getReceptor().equals(EmisorUid) && chat.getEmisor().equals(ReceptorUid)
                        || chat.getReceptor().equals(ReceptorUid) && chat.getEmisor().equals(EmisorUid)){

                        (chatList as ArrayList<ChatModelo>).add(chat)


                    }

                    chatadapter = AdaptadorChat(this@MensajesActivity, (chatList as ArrayList<ChatModelo>),ReceptorImagen!!)
                    RV_chats.adapter = chatadapter


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun AbrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type= "image/*"
        galeriaURL.launch(intent)


    }

    private val galeriaURL = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{resultado->
            if (resultado.resultCode == RESULT_OK){
                val data = resultado.data
                imagen2Uri = data!!.data

               //se esta cargando imagen
                val cargandoImagen = ProgressDialog(this@MensajesActivity)
                cargandoImagen.setMessage("Por favor espere, la imagen se esta enviando")
                cargandoImagen.setCanceledOnTouchOutside(false)
                cargandoImagen.show()

                val carpetaImagenes = FirebaseStorage.getInstance().reference.child("Imagenes de mensajes")
                val reference = FirebaseDatabase.getInstance().reference
                val idMensaje = reference.push().key
                val nombreImagen = carpetaImagenes.child("$idMensaje.jpg")

                val uploadTask : StorageTask<*>
                uploadTask = nombreImagen.putFile(imagen2Uri!!)
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {task ->
                    if (!task.isSuccessful){
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation nombreImagen.downloadUrl
                }).addOnCompleteListener{task->
                    if (task.isSuccessful){
                        cargandoImagen.dismiss()
                        val downloadUrl = task.result
                        val url = downloadUrl.toString()
                        val infoMensajeImagen = HashMap<String, Any?>()

                        infoMensajeImagen["id_imagen"]= idMensaje
                        infoMensajeImagen["emisor"]= FirebaseUser!!.uid
                        infoMensajeImagen["receptor"]= uid_usuario_seleccionado
                        infoMensajeImagen["mensaje"]= "Se ha enviado la imagen"
                        infoMensajeImagen["url"]= url
                        infoMensajeImagen["visto"]= false

                        reference.child("Chats").child(idMensaje!!).setValue(infoMensajeImagen)
                            .addOnCompleteListener {tarea->
                                if (tarea.isSuccessful){
                                    val ListaMensajeEmisor = FirebaseDatabase.getInstance().reference.child("ListaMensajes")
                                        .child(FirebaseUser!!.uid)
                                        .child(uid_usuario_seleccionado)

                                    ListaMensajeEmisor.addListenerForSingleValueEvent(object  : ValueEventListener{
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            if (!snapshot.exists()){
                                                ListaMensajeEmisor.child("uid").setValue(uid_usuario_seleccionado)
                                            }

                                            val  ListaMensajereceptor = FirebaseDatabase.getInstance().reference.child("ListaMensajes")
                                                .child(uid_usuario_seleccionado)
                                                .child(FirebaseUser!!.uid)
                                            ListaMensajereceptor.child("uid").setValue(FirebaseUser!!.uid)
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                    })
                                }
                            }
                        Toast.makeText(applicationContext,"La Imagen se ha enviado",Toast.LENGTH_SHORT).show()


                    }


                }


            }
            else{
                Toast.makeText(applicationContext,"Cancelado por el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    )

}