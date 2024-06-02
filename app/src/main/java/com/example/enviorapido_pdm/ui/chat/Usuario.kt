package com.example.enviorapido_pdm.ui.chat

class Usuario {
    private var uid : String = ""
    private var n_usuario : String = ""
    private var email : String = ""
    private var estado : String = ""
    private var imagen : String = ""
    private var buscar : String = ""
    private var nombres : String = ""
    private var apellidos : String = ""


    constructor()

    constructor(
        uid: String,
        n_usuario: String,
        email: String,
        estado: String,
        imagen: String,
        buscar: String,
        nombres: String,
        apellidos: String

    ) {
        this.uid = uid
        this.n_usuario = n_usuario
        this.email = email
        this.estado = estado
        this.imagen = imagen
        this.buscar = buscar
        this.nombres = nombres
        this.apellidos = apellidos

    }

    //getters y setters
    fun getUid() : String?{
        return uid
    }

    fun setUid(uid : String){
        this.uid = uid
    }

    fun getN_Usuario() : String?{
        return n_usuario
    }

    fun setN_Usuario(n_usuario : String){
        this.n_usuario = n_usuario
    }

    fun getEmail() : String?{
        return email
    }

    fun setEmail(email : String){
        this.email = email
    }

    fun getEstado() : String?{
        return estado
    }

    fun setEstado(estado: String){
        this.estado = estado
    }

    fun getImagen() : String?{
        return imagen
    }

    fun setImagen(imagen : String){
        this.imagen = imagen
    }

    fun getBuscar() : String?{
        return buscar
    }

    fun setBuscar(buscar : String){
        this.buscar = buscar
    }

    fun getNombres() : String?{
        return nombres
    }

    fun setNombres(nombres : String){
        this.nombres = nombres
    }

    fun getApellidos() : String?{
        return apellidos
    }

    fun setApellidos(apellidos : String){
        this.apellidos = apellidos
    }


}