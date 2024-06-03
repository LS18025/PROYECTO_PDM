package com.example.enviorapido_pdm.ui.usuario

data class Usuarios(
    val id_usuario: String,
    val rol :String,
    val primer_nombre_persona:String,
    val primer_apellido_persona:String,
    val email_persona:String,
    val telefono:String,
    val usuario:String,
    val contrasena:String) {
}