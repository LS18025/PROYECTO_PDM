package com.example.enviorapido_pdm.ui.envios

data class Envios(
        val id: Int,
        val idUsuario: String,
        val direccion: String,
        val destinatario: String,
        val idTransportista: Int,
        val nombreTransportista: String, // Nuevo campo
        val etiqueta: String,
        val costoTotal: Double,
        val fechaEnvio: String,
        val fechaProgramada: String,
        val numeroConf: String
)

{

}